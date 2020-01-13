package co.uk.akm.test.postlist.data.source.impl.db

import android.app.Application
import co.uk.akm.test.postlist.data.entity.PostEntity
import co.uk.akm.test.postlist.data.source.PostDataSource
import co.uk.akm.test.postlist.data.source.impl.db.provider.DbProvider
import co.uk.akm.test.postlist.data.source.impl.db.provider.DefaultDbProvider
import co.uk.akm.test.postlist.data.source.impl.db.provider.DefaultTimeProvider
import co.uk.akm.test.postlist.data.source.impl.db.provider.TimeProvider
import io.reactivex.Single

/**
 * Accepting Application instances to prevent the accidental passing of a non-application context.
 * The providers are not necessary. They are used so that, in unit tests, mock providers can be
 * supplied.
 */
class DbPostDataSource(
    app: Application,
    expiryTimeInSecs: Int,
    dbProvider: DbProvider = DefaultDbProvider(),
    private val timeProvider: TimeProvider = DefaultTimeProvider()
) : PostDataSource {

    private companion object {
        const val SECS_TO_MILLIS = 1000L
    }

    private val dao = dbProvider.dbInstance(app).postDao()
    private val expiryTimeInMillis = expiryTimeInSecs*SECS_TO_MILLIS

    /**
     * Number and time check. If we have less posts in our DB than the number we are asked for, then
     * this method returns false since we definitely we do not have the latest posts. If we have enough
     * posts then we check the timestamp of the earliest post. If it is earlier that our minimum allowed
     * time (based on the input cache age) then we return false. Otherwise we return true.
     */
    override fun hasLatestPosts(number: Int): Single<Boolean> {
        val numberCheck: () -> Single<Boolean> = { dao.getNumberOfPosts().map { it >= number } }
        val expiryCheck: () -> Single<Boolean> = { dao.getEarliestPostTimestamp().map { notExpired(it) } }

        val expiryCheckIfNeeded: (Boolean) -> Single<Boolean> = { numberCheckPassed ->
            if (numberCheckPassed.not()) {
                Single.just(false)
            } else {
                expiryCheck.invoke()
            }
        }

        return numberCheck.invoke().flatMap { expiryCheckIfNeeded.invoke(it) }
    }

    private fun notExpired(timestamp: Long): Boolean {
        return (System.currentTimeMillis() - timestamp <= expiryTimeInMillis)
    }

    override fun getPosts(number: Int): Single<List<PostEntity>> {
        return dao.getPosts().map {
            if (it.size > number) { // Just in case we have cached more than we are asked.
                it.slice(0 until number)
            } else {
                it
            }
        }
    }

    override fun cachePosts(posts: List<PostEntity>) {
        timestampThePosts(posts)

        with(dao) {
            deletePosts()
            insertPosts(posts)
        }
    }

    private fun timestampThePosts(posts: List<PostEntity>) {
        val now = timeProvider.now()
        posts.forEach { it.timestamp = now }
    }
}