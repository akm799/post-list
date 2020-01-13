package co.uk.akm.test.postlist.data.repo.impl

import co.uk.akm.test.postlist.data.entity.UserEntity
import co.uk.akm.test.postlist.data.source.UserDataSource
import co.uk.akm.test.postlist.domain.model.User
import co.uk.akm.test.postlist.domain.repo.UserRepository
import co.uk.akm.test.postlist.util.rx.readOrFetchEntity
import io.reactivex.Single

class UserRepositoryImpl(
    private val dbSource: UserDataSource,
    private val remoteSource: UserDataSource
) : UserRepository {

    override fun getUser(userId: Int): Single<User> {
        return readOrFetchUser(userId).map { User(it.id, it.username) }
    }

    private fun readOrFetchUser(userId: Int): Single<UserEntity> {
        val isUserCached = { dbSource.hasUser(userId) }
        val readUser = { dbSource.getUser(userId) }
        val fetchUser = { remoteSource.getUser(userId) }
        val cacheUser = { user: UserEntity -> dbSource.cacheUser(user) }

        return readOrFetchEntity(isUserCached, readUser, fetchUser, cacheUser)
    }
}