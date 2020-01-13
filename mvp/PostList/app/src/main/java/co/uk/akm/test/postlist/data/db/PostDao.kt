package co.uk.akm.test.postlist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.uk.akm.test.postlist.data.entity.PostEntity
import io.reactivex.Single

@Dao
abstract class PostDao {

    @Query("SELECT count(*) FROM posts")
    abstract fun getNumberOfPosts(): Single<Int>

    @Query("SELECT MIN(timestamp) FROM posts")
    abstract fun getEarliestPostTimestamp(): Single<Long>

    @Query("SELECT * FROM posts")
    abstract fun getPosts(): Single<List<PostEntity>>

    @Query("DELETE FROM posts")
    abstract fun deletePosts(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPosts(posts: List<PostEntity>) // Cannot return anything from here due to a room-rxjava2 bug.
}