package co.uk.akm.test.postlist.data.source.impl.db

import android.app.Application
import co.uk.akm.test.postlist.data.db.UserPostDatabase
import co.uk.akm.test.postlist.data.source.impl.db.provider.DbProvider
import org.junit.Assert

class TestDbProvider(
    private val app: Application,
    private val db: UserPostDatabase
) : DbProvider {

    override fun dbInstance(app: Application): UserPostDatabase {
        Assert.assertEquals(this.app, app)

        return db
    }
}