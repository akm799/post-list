package co.uk.akm.test.postlist.util.rx

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Helper function that can fetch a list of items and then for each item fetch the item details. It
 * then combines each item with its details and returns a list of "rich" items, i.e. a list of item
 * objects with each element also containing the item details.
 */
fun <I, D, C> getItemsWithDetails(
    getItems: () -> Single<List<I>>,
    getItemDetails: (I) -> Single<D>,
    combine: (I, D) -> C
) : Single<List<C>> {

    val getDetailsAndCombine: (I) -> Observable<C> = { item ->
        getItemDetails(item).map { details -> combine(item, details) }.toObservable()
    }

    return getItems.invoke().flatMap { items ->
        Observable.fromIterable(items).flatMap { item -> getDetailsAndCombine(item) }.toList()
    }
}

/**
 * Helper function that checks if an entity is cached, and returns it from the cache if so, or fetches
 * it from a remote source, and caches it before returning it.
 */
fun <T> readOrFetchEntity(
    isCached: () -> Single<Boolean>,
    readFromCache: () -> Single<T>,
    fetch: () -> Single<T>,
    cache: (T) -> Unit
): Single<T> {
    val readOrFetch: (Boolean) -> Single<T> = { cached ->
        if (cached) {
            readFromCache.invoke()
        } else {
            fetch.invoke().map { cacheEntity(it, cache) }
        }
    }

    return isCached.invoke().flatMap(readOrFetch)
}

private fun <T> cacheEntity(entity: T, cache: (T) -> Unit): T {
    return entity.apply { cache.invoke(this) }
}