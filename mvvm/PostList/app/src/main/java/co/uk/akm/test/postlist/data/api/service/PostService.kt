package co.uk.akm.test.postlist.data.api.service

import co.uk.akm.test.postlist.data.entity.PostEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("/posts")
    fun getPosts(@Query("_limit") number: Int): Single<List<PostEntity>>
}