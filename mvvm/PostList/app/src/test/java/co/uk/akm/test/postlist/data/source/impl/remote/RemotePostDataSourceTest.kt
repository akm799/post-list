package co.uk.akm.test.postlist.data.source.impl.remote

import co.uk.akm.test.postlist.data.api.service.PostService
import co.uk.akm.test.postlist.data.entity.PostEntity
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class RemotePostDataSourceTest {

    @Test
    fun shouldGetPosts() {
        val number = 1
        val posts = listOf(PostEntity(99, "The Post Title", 42))

        val service = Mockito.mock(PostService::class.java)
        Mockito.`when`(service.getPosts(number)).thenReturn(Single.just(posts))

        val underTest = RemotePostDataSource(service)
        val fetchedPosts = underTest.getPosts(number).blockingGet()
        Assert.assertEquals(posts, fetchedPosts)
    }
}