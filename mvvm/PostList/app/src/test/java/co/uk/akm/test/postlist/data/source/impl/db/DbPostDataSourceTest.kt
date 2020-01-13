package co.uk.akm.test.postlist.data.source.impl.db

import android.app.Application
import co.uk.akm.test.postlist.data.db.PostDao
import co.uk.akm.test.postlist.data.db.UserPostDatabase
import co.uk.akm.test.postlist.data.entity.PostEntity
import co.uk.akm.test.postlist.data.source.impl.db.provider.DbProvider
import co.uk.akm.test.postlist.data.source.impl.db.provider.TimeProvider
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito

class DbPostDataSourceTest {
    private val number = 1
    private val post = PostEntity(42, "Title", 99)
    private val posts = listOf(post)

    private val tenMinutesInSecs = 600

    @Test
    fun shouldShowPostsStored() {
        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(PostDao::class.java)
        Mockito.`when`(dao.getNumberOfPosts()).thenReturn(Single.just(number))
        Mockito.`when`(dao.getEarliestPostTimestamp()).thenReturn(Single.just(System.currentTimeMillis()))

        val underTest = DbPostDataSource(app, tenMinutesInSecs, testDbProvider(app, dao))
        val hasPosts = underTest.hasLatestPosts(number).blockingGet()
        Assert.assertTrue(hasPosts)
    }

    @Test
    fun shouldShowPostsNotStoredWhenAskingForMore() {
        val higherNumber = number + number
        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(PostDao::class.java)
        Mockito.`when`(dao.getNumberOfPosts()).thenReturn(Single.just(number))

        val underTest = DbPostDataSource(app, tenMinutesInSecs, testDbProvider(app, dao))
        val hasPosts = underTest.hasLatestPosts(higherNumber).blockingGet()
        Assert.assertFalse(hasPosts)
        Mockito.verify(dao, Mockito.never()).getEarliestPostTimestamp()
    }

    @Test
    fun shouldShowPostsNotStoredWhenStoredPostsAreTooOld() {
        val secsToMillis = 1000L
        val moreThanTenMinutesInSecs = tenMinutesInSecs + tenMinutesInSecs
        val tooLongAgoMillis = System.currentTimeMillis() - secsToMillis*moreThanTenMinutesInSecs

        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(PostDao::class.java)
        Mockito.`when`(dao.getNumberOfPosts()).thenReturn(Single.just(number))
        Mockito.`when`(dao.getEarliestPostTimestamp()).thenReturn(Single.just(tooLongAgoMillis))

        val underTest = DbPostDataSource(app, tenMinutesInSecs, testDbProvider(app, dao))
        val hasPosts = underTest.hasLatestPosts(number).blockingGet()
        Assert.assertFalse(hasPosts)
    }

    @Test
    fun shouldGetPostsStored() {
        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(PostDao::class.java)
        Mockito.`when`(dao.getPosts()).thenReturn(Single.just(posts))

        val underTest = DbPostDataSource(app, tenMinutesInSecs, testDbProvider(app, dao))
        val storedPosts = underTest.getPosts(number).blockingGet()
        Assert.assertEquals(posts, storedPosts)
    }

    @Test
    fun shouldGetOnlyAskedPostsStored() {
        val allPosts = listOf(post, PostEntity(43, "Next Title", 71))

        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(PostDao::class.java)
        Mockito.`when`(dao.getPosts()).thenReturn(Single.just(allPosts))

        val underTest = DbPostDataSource(app, tenMinutesInSecs, testDbProvider(app, dao))
        val storedPosts = underTest.getPosts(number).blockingGet()
        Assert.assertEquals(posts, storedPosts) // We only asked for less posts than stored so we are getting just what we asked.
    }

    @Test
    fun shouldCachePosts() {
        val posts = listOf(PostEntity(57, "Random Title", 33))

        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(PostDao::class.java)

        val now = System.currentTimeMillis()
        val timeProvider = object : TimeProvider {
            override fun now(): Long = now
        }

        val underTest = DbPostDataSource(app, tenMinutesInSecs, testDbProvider(app, dao), timeProvider)

        posts.forEach { Assert.assertEquals(0, it.timestamp) }
        underTest.cachePosts(posts)
        // Check the cache timestamps.
        posts.forEach { Assert.assertEquals(now, it.timestamp) }

        val order = Mockito.inOrder(dao, dao)
        order.verify(dao).deletePosts()
        order.verify(dao).insertPosts(posts)
    }

    private fun testDbProvider(app: Application, dao: PostDao): DbProvider {
        val db = Mockito.mock(UserPostDatabase::class.java)
        Mockito.`when`(db.postDao()).thenReturn(dao)

        return TestDbProvider(app, db)
    }
}