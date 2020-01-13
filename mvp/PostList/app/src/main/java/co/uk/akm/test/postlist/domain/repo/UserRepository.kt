package co.uk.akm.test.postlist.domain.repo

import co.uk.akm.test.postlist.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun getUser(userId: Int): Single<User>
}