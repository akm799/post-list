package co.uk.akm.test.postlist.data.source.impl.db

import android.app.Application
import co.uk.akm.test.postlist.data.entity.UserEntity
import co.uk.akm.test.postlist.data.source.UserDataSource
import co.uk.akm.test.postlist.data.source.impl.db.provider.DbProvider
import co.uk.akm.test.postlist.data.source.impl.db.provider.DefaultDbProvider
import io.reactivex.Single

/**
 * Accepting Application instances to prevent the accidental passing of a non-application context.
 * The DB provider is not necessary. It is used so that, in unit tests, mock DB providers can be
 * supplied.
 */
class DbUserDataSource(
    app: Application,
    dbProvider: DbProvider = DefaultDbProvider()
) : UserDataSource {

    private val dao = dbProvider.dbInstance(app).userDao()

    override fun hasUser(userId: Int): Single<Boolean> = dao.hasUser(userId).map { it == 1 }

    override fun getUser(userId: Int): Single<UserEntity> = dao.getUser(userId)

    override fun cacheUser(user: UserEntity) {
        dao.insertUser(user)
    }
}