package co.uk.akm.test.postlist.data.source.impl.remote

import co.uk.akm.test.postlist.data.api.service.UserService
import co.uk.akm.test.postlist.data.entity.UserEntity
import co.uk.akm.test.postlist.data.source.UserDataSource
import io.reactivex.Single

/**
 * The methods to check for a user and cache one are not used in this remote server implementation.
 * They are present so that the remote and local data source implementations cannot be distinguished
 * from their contract.
 */
class RemoteUserDataSource(private val service: UserService) : UserDataSource {

    // Not used
    override fun hasUser(userId: Int): Single<Boolean> = Single.just(true)

    override fun getUser(userId: Int): Single<UserEntity> = service.getUser(userId)

    // Not used
    override fun cacheUser(user: UserEntity) {}
}