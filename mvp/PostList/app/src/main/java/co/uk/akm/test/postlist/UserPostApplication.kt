package co.uk.akm.test.postlist

import android.app.Application
import co.uk.akm.test.postlist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UserPostApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@UserPostApplication)
            modules(appModule)
        }
    }
}