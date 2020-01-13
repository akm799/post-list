package co.uk.akm.test.postlist.data.source.impl.remote

import co.uk.akm.test.postlist.data.api.service.UserService
import co.uk.akm.test.postlist.data.entity.UserEntity
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class RemoteUserDataSourceTest {

    @Test
    fun shouldGetUser() {
        val user = UserEntity(42, "john")

        val service = Mockito.mock(UserService::class.java)
        Mockito.`when`(service.getUser(user.id)).thenReturn(Single.just(user))

        val underTest = RemoteUserDataSource(service)
        val fetchedUser = underTest.getUser(user.id).blockingGet()
        Assert.assertEquals(user, fetchedUser)
    }
}