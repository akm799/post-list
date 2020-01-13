package co.uk.akm.test.postlist.domain.repo

import co.uk.akm.test.postlist.domain.model.Post
import io.reactivex.Single

interface PostRepository {

    fun getPosts(number: Int): Single<List<Post>>
}