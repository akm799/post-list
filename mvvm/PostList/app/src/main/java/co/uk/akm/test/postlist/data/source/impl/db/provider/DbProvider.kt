package co.uk.akm.test.postlist.data.source.impl.db.provider

import android.app.Application
import co.uk.akm.test.postlist.data.db.UserPostDatabase

interface DbProvider {

    fun dbInstance(app: Application): UserPostDatabase
}