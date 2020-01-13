package co.uk.akm.test.postlist.data.repo.impl

import co.uk.akm.test.postlist.data.entity.UserEntity
import co.uk.akm.test.postlist.data.source.UserDataSource
import co.uk.akm.test.postlist.domain.model.User
import co.uk.akm.test.postlist.helper.KAny
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class UserRepositoryImplTest {
    private val user = User(42, "john")
    private val userEntity = UserEntity(user.id, user.name)

    @Test
    fun shouldReadUser() {
        val local = Mockito.mock(UserDataSource::class.java)
        val remote = Mockito.mock(UserDataSource::class.java)

        Mockito.`when`(local.hasUser(user.id)).thenReturn(Single.just(true))
        Mockito.`when`(local.getUser(user.id)).thenReturn(Single.just(userEntity))

        val underTest = UserRepositoryImpl(local, remote)
        val readUser = underTest.getUser(user.id).blockingGet()
        Assert.assertEquals(user, readUser)
        Mockito.verify(remote, Mockito.never()).getUser(Mockito.anyInt())
        Mockito.verify(remote, Mockito.never()).cacheUser(KAny.any(UserEntity(0, "")))
    }

    @Test
    fun shouldFetchUser() {
        val local = Mockito.mock(UserDataSource::class.java)
        val remote = Mockito.mock(UserDataSource::class.java)

        Mockito.`when`(local.hasUser(user.id)).thenReturn(Single.just(false))
        Mockito.`when`(remote.getUser(user.id)).thenReturn(Single.just(userEntity))

        val underTest = UserRepositoryImpl(local, remote)
        val fetchedUser = underTest.getUser(user.id).blockingGet()
        Assert.assertEquals(user, fetchedUser)
        Mockito.verify(local).cacheUser(userEntity)
        Mockito.verify(local, Mockito.never()).getUser(Mockito.anyInt())
    }
}