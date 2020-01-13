package co.uk.akm.test.postlist.domain.interactor.impl

import co.uk.akm.test.postlist.domain.model.Post
import co.uk.akm.test.postlist.domain.model.User
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.domain.repo.PostRepository
import co.uk.akm.test.postlist.domain.repo.UserRepository
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class UserPostUseCaseImplTest {

    @Test
    fun shouldFetchPostsAndUsers() {
        val number = 1
        val user = User(42, "john")
        val post = Post(99, "Post Title", user.id)
        val posts = listOf(post)

        val postRepo = Mockito.mock(PostRepository::class.java)
        Mockito.`when`(postRepo.getPosts(number)).thenReturn(Single.just(posts))

        val userRepo = Mockito.mock(UserRepository::class.java)
        Mockito.`when`(userRepo.getUser(user.id)).thenReturn(Single.just(user))

        val expected = listOf(UserPost(post.id, post.title, user.name))
        val underTest = UserPostUseCaseImpl(postRepo, userRepo)
        val actual = underTest.getUserPosts(number).blockingGet()
        Assert.assertEquals(expected, actual)
    }
}