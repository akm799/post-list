package co.uk.akm.test.postlist.domain.interactor.impl

import co.uk.akm.test.postlist.domain.extensions.with
import co.uk.akm.test.postlist.domain.interactor.UserPostUseCase
import co.uk.akm.test.postlist.domain.model.Post
import co.uk.akm.test.postlist.domain.model.User
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.domain.repo.PostRepository
import co.uk.akm.test.postlist.domain.repo.UserRepository
import co.uk.akm.test.postlist.util.rx.getItemsWithDetails
import io.reactivex.Single

class UserPostUseCaseImpl(
    private val postRepo: PostRepository,
    private val userRepo: UserRepository
) : UserPostUseCase {

    override fun getUserPosts(number: Int): Single<List<UserPost>> {
        val getPosts: () -> Single<List<Post>> = { postRepo.getPosts(number) }

        val getUser: (Post) -> Single<User> = { post -> userRepo.getUser(post.userId) }

        val combinePostAndUser: (Post, User) -> UserPost = { post, user -> post with user }

        return getItemsWithDetails(getPosts, getUser, combinePostAndUser)
    }
}