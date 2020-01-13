package co.uk.akm.test.postlist.util.rx

import io.reactivex.Single
import org.junit.Assert
import org.junit.Test

class RxFunctionsTest {

    @Test
    fun shouldGetItemsWithDetails() {
        val items = listOf(1, 2, 3, 4, 5)
        val itemDetails = linkedMapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five")

        val getItems = { Single.just(items) }
        val getDetails = { item: Int -> Single.just(itemDetails[item]!!) }
        val combine = { item: Int, details: String -> Pair(item, details) }

        val expected = itemDetails.entries.map { Pair(it.key, it.value) }
        val actual = getItemsWithDetails(getItems, getDetails, combine).blockingGet()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReadItemFromCache() {
        val cached = "cached-item"

        val isCached = { Single.just(true) } // Cached: read from our cache instead of fetching from the server.
        val read = { Single.just(cached) }
        val fetch = { Single.error<String>(AssertionError("Tried to fetch from server when item is cached.")) }
        val cache = { value: String -> Assert.fail("Cache method called when item is already cached.") }

        val item = readOrFetchEntity(isCached, read, fetch, cache).blockingGet()
        Assert.assertEquals(cached, item)
    }

    @Test
    fun shouldFetchItemFromServer() {
        val remote = "server-item"
        var remoteCached: String? = null

        val isCached = { Single.just(false) } // Not cached: have to fetch from the server.
        val read = { Single.error<String>(AssertionError("Tried to read from cache when item is not cached.")) }
        val fetch = { Single.just(remote) }
        val cache = { value: String -> remoteCached = value }

        val item = readOrFetchEntity(isCached, read, fetch, cache).blockingGet()
        Assert.assertEquals(remote, item)
        Assert.assertEquals(remote, remoteCached) // Check that the item we fetched was cached.
    }
}