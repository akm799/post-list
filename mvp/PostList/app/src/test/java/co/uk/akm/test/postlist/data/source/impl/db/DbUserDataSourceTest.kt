package co.uk.akm.test.postlist.data.source.impl.db

import android.app.Application
import co.uk.akm.test.postlist.data.db.UserDao
import co.uk.akm.test.postlist.data.db.UserPostDatabase
import co.uk.akm.test.postlist.data.entity.UserEntity
import co.uk.akm.test.postlist.data.source.impl.db.provider.DbProvider
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class DbUserDataSourceTest {
    private val user = UserEntity(42, "john")

    @Test
    fun shouldShowUserStored() {
        testHasUser(true)
    }

    @Test
    fun shouldShowUserNotStored() {
        testHasUser(false)
    }

    private fun testHasUser(expected: Boolean) {
        val app = Mockito.mock(Application::class.java)

        val userCount = if (expected) 1 else 0
        val dao = Mockito.mock(UserDao::class.java)
        Mockito.`when`(dao.hasUser(user.id)).thenReturn(Single.just(userCount))

        val underTest = DbUserDataSource(app, testDbProvider(app, dao))
        val hasUser = underTest.hasUser(user.id).blockingGet()
        Assert.assertEquals(expected, hasUser)
    }

    @Test
    fun shouldGetUser() {
        val app = Mockito.mock(Application::class.java)

        val dao = Mockito.mock(UserDao::class.java)
        Mockito.`when`(dao.getUser(user.id)).thenReturn(Single.just(user))

        val underTest = DbUserDataSource(app, testDbProvider(app, dao))
        val user = underTest.getUser(user.id).blockingGet()
        Assert.assertEquals(this.user, user)
    }

    @Test
    fun shouldCacheUser() {
        val app = Mockito.mock(Application::class.java)
        val dao = Mockito.mock(UserDao::class.java)

        val underTest = DbUserDataSource(app, testDbProvider(app, dao))
        underTest.cacheUser(user)
        Mockito.verify(dao).insertUser(user)
    }

    private fun testDbProvider(app: Application, dao: UserDao): DbProvider {
        val db = Mockito.mock(UserPostDatabase::class.java)
        Mockito.`when`(db.userDao()).thenReturn(dao)

        return TestDbProvider(app, db)
    }
}