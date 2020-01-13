package co.uk.akm.test.postlist.data.source

import co.uk.akm.test.postlist.data.entity.UserEntity
import io.reactivex.Single

interface UserDataSource {

    fun hasUser(userId: Int): Single<Boolean>

    fun getUser(userId: Int): Single<UserEntity>

    fun cacheUser(user: UserEntity)
}