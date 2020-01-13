package co.uk.akm.test.postlist.data.repo.impl

import co.uk.akm.test.postlist.data.entity.PostEntity
import co.uk.akm.test.postlist.data.source.PostDataSource
import co.uk.akm.test.postlist.domain.model.Post
import co.uk.akm.test.postlist.helper.KAny
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class PostRepositoryImplTest {
    private val number = 1
    private val post = Post(42, "Post Title", 99)
    private val postEntity = PostEntity(post.id, post.title, post.userId)
    private val postList = listOf(post)
    private val postEntityList = listOf(postEntity)

    @Test
    fun shouldReadPosts() {
        val local = Mockito.mock(PostDataSource::class.java)
        val remote = Mockito.mock(PostDataSource::class.java)

        Mockito.`when`(local.hasLatestPosts(number)).thenReturn(Single.just(true))
        Mockito.`when`(local.getPosts(number)).thenReturn(Single.just(postEntityList))

        val underTest = PostRepositoryImpl(local, remote)
        val readPosts = underTest.getPosts(number).blockingGet()
        Assert.assertEquals(postList, readPosts)
        Mockito.verify(local, Mockito.never()).cachePosts(KAny.any(emptyList()))
        Mockito.verify(remote, Mockito.never()).getPosts(Mockito.anyInt())
    }

    @Test
    fun shouldFetchPosts() {
        val local = Mockito.mock(PostDataSource::class.java)
        val remote = Mockito.mock(PostDataSource::class.java)

        Mockito.`when`(local.hasLatestPosts(number)).thenReturn(Single.just(false))
        Mockito.`when`(remote.getPosts(number)).thenReturn(Single.just(postEntityList))

        val underTest = PostRepositoryImpl(local, remote)
        val fetchedPosts = underTest.getPosts(number).blockingGet()
        Assert.assertEquals(postList, fetchedPosts)
        Mockito.verify(local).cachePosts(postEntityList)
        Mockito.verify(local, Mockito.never()).getPosts(Mockito.anyInt())
    }
}