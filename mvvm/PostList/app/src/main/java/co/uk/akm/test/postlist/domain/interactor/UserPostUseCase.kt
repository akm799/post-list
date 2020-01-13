package co.uk.akm.test.postlist.domain.interactor

import co.uk.akm.test.postlist.domain.model.UserPost
import io.reactivex.Single

interface UserPostUseCase {

    fun getUserPosts(number: Int): Single<List<UserPost>>
}