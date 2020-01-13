package co.uk.akm.test.postlist.data.source.impl.db.provider

import android.app.Application
import co.uk.akm.test.postlist.data.db.UserPostDatabase

class DefaultDbProvider : DbProvider {
    override fun dbInstance(app: Application): UserPostDatabase = UserPostDatabase.singleInstance(app)
}