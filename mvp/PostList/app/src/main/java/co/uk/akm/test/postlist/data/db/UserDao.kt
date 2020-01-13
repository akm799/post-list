package co.uk.akm.test.postlist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.uk.akm.test.postlist.data.entity.UserEntity
import io.reactivex.Single

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(user: UserEntity) // Cannot return anything from here due to a room-rxjava2 bug.

    @Query("SELECT count(*) FROM users WHERE id = :userId")
    abstract fun hasUser(userId: Int): Single<Int>

    @Query("SELECT * FROM users WHERE id = :userId")
    abstract fun getUser(userId: Int): Single<UserEntity>
}