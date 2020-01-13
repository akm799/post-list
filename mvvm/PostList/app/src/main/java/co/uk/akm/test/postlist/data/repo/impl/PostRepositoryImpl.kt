package co.uk.akm.test.postlist.data.repo.impl

import co.uk.akm.test.postlist.data.entity.PostEntity
import co.uk.akm.test.postlist.data.source.PostDataSource
import co.uk.akm.test.postlist.domain.model.Post
import co.uk.akm.test.postlist.domain.repo.PostRepository
import co.uk.akm.test.postlist.util.rx.readOrFetchEntity
import io.reactivex.Single

class PostRepositoryImpl(
    private val dbSource: PostDataSource,
    private val remoteSource: PostDataSource
) : PostRepository {

    override fun getPosts(number: Int): Single<List<Post>> {
        return readOrFetchPosts(number).map { entities ->
            entities.map { Post(it.id, it.title, it.userId) }
        }
    }

    private fun readOrFetchPosts(number: Int): Single<List<PostEntity>> {
        val arePostsCached = { dbSource.hasLatestPosts(number) }
        val readPosts = { dbSource.getPosts(number) }
        val fetchPosts = { remoteSource.getPosts(number) }
        val cachePosts = { posts: List<PostEntity> -> dbSource.cachePosts(posts) }

        return readOrFetchEntity(arePostsCached, readPosts, fetchPosts, cachePosts)
    }
}