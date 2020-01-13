package co.uk.akm.test.postlist.data.source.impl.remote

import co.uk.akm.test.postlist.data.api.service.PostService
import co.uk.akm.test.postlist.data.entity.PostEntity
import co.uk.akm.test.postlist.data.source.PostDataSource
import io.reactivex.Single

/**
 * The methods to check for the latest posts and cache posts are not used in this remote server
 * implementation. They are present so that the remote and local data source implementations cannot
 * be distinguished from their contract.
 */
class RemotePostDataSource(private val service: PostService) : PostDataSource {

    // Not used
    override fun hasLatestPosts(number: Int): Single<Boolean> = Single.just(true)

    override fun getPosts(number: Int): Single<List<PostEntity>> = service.getPosts(number)

    // Not used
    override fun cachePosts(posts: List<PostEntity>) {}
}