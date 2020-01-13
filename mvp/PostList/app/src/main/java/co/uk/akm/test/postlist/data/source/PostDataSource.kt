package co.uk.akm.test.postlist.data.source

import co.uk.akm.test.postlist.data.entity.PostEntity
import io.reactivex.Single

interface PostDataSource {

    fun hasLatestPosts(number: Int): Single<Boolean>

    fun getPosts(number: Int): Single<List<PostEntity>>

    fun cachePosts(posts: List<PostEntity>)
}