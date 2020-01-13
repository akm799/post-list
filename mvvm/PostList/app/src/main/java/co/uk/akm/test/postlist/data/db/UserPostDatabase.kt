package co.uk.akm.test.postlist.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.uk.akm.test.postlist.data.entity.PostEntity
import co.uk.akm.test.postlist.data.entity.UserEntity

@Database(entities = arrayOf(PostEntity::class, UserEntity::class), version = 1)
abstract class UserPostDatabase : RoomDatabase() {
    companion object {
        private const val USER_POSTS_DB_NAME = "user-posts-db"

        @Volatile
        private var INSTANCE: UserPostDatabase? = null

        /**
         * Accepting Application instances to prevent the accidental passing of a non-application context.
         */
        fun singleInstance(app: Application): UserPostDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(app, USER_POSTS_DB_NAME).also { INSTANCE = it }
            }
        }

        private fun build(context: Context, dbName: String): UserPostDatabase {
            return Room.databaseBuilder(context.applicationContext, UserPostDatabase::class.java, dbName).build()
        }

        fun closeInstance() {
            if (INSTANCE?.isOpen == true) {
                INSTANCE?.close()
            }

            INSTANCE = null
        }
    }

    abstract fun postDao(): PostDao

    abstract fun userDao(): UserDao
}