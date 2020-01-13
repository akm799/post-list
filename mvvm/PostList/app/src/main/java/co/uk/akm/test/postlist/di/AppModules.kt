package co.uk.akm.test.postlist.di

import co.uk.akm.test.postlist.BuildConfig
import co.uk.akm.test.postlist.data.api.service.PostService
import co.uk.akm.test.postlist.data.api.service.UserService
import co.uk.akm.test.postlist.data.repo.impl.PostRepositoryImpl
import co.uk.akm.test.postlist.data.repo.impl.UserRepositoryImpl
import co.uk.akm.test.postlist.data.source.PostDataSource
import co.uk.akm.test.postlist.data.source.UserDataSource
import co.uk.akm.test.postlist.data.source.impl.db.DbPostDataSource
import co.uk.akm.test.postlist.data.source.impl.db.DbUserDataSource
import co.uk.akm.test.postlist.data.source.impl.remote.RemotePostDataSource
import co.uk.akm.test.postlist.data.source.impl.remote.RemoteUserDataSource
import co.uk.akm.test.postlist.domain.interactor.UserPostUseCase
import co.uk.akm.test.postlist.domain.interactor.impl.UserPostUseCaseImpl
import co.uk.akm.test.postlist.domain.repo.PostRepository
import co.uk.akm.test.postlist.domain.repo.UserRepository
import co.uk.akm.test.postlist.presentation.viewmodel.UserPostViewModel
import co.uk.akm.test.postlist.util.retrofit.retrofitInstance
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single<Gson> { GsonBuilder().setLenient().create() }

    single { retrofitInstance(BuildConfig.API_BASE_URL, get()) }

    single { (get() as Retrofit).create(PostService::class.java) }

    single { (get() as Retrofit).create(UserService::class.java) }

    single<PostDataSource>(named("cachedPosts")) { DbPostDataSource(androidApplication(), BuildConfig.CACHE_EXPIRY_SECS) }

    single<PostDataSource>(named("remotePosts")) { RemotePostDataSource(get()) }

    single<UserDataSource>(named("cachedUsers")) { DbUserDataSource(androidApplication()) }

    single<UserDataSource>(named("remoteUsers")) { RemoteUserDataSource(get()) }

    single<PostRepository> { PostRepositoryImpl(get(named("cachedPosts")), get(named("remotePosts"))) }

    single<UserRepository> { UserRepositoryImpl(get(named("cachedUsers")), get(named("remoteUsers"))) }

    single<UserPostUseCase> { UserPostUseCaseImpl(get(), get()) }

    viewModel { UserPostViewModel(get()) }
}