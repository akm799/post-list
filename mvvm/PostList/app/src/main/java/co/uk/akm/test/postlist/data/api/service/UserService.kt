package co.uk.akm.test.postlist.data.api.service

import co.uk.akm.test.postlist.data.entity.UserEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserEntity>
}