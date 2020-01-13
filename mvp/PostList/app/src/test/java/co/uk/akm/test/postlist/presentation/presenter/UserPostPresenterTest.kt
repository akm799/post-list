package co.uk.akm.test.postlist.presentation.presenter

import co.uk.akm.test.postlist.domain.interactor.UserPostUseCase
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.helper.KAny
import co.uk.akm.test.postlist.presentation.UserPostMVP
import co.uk.akm.test.postlist.presentation.presenter.util.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.mockito.Mockito

class UserPostPresenterTest {
    private val number = 1
    private val posts = listOf(UserPost(42, "Post Title", "john"))

    @Test
    fun shouldShowFetchedUserPosts() {
        val useCase = Mockito.mock(UserPostUseCase::class.java)
        Mockito.`when`(useCase.getUserPosts(number)).thenReturn(Single.just(posts))

        val io = TestScheduler()
        val ui = TestScheduler()
        val underTest = UserPostPresenter(useCase, TestSchedulerProvider(io, ui))

        val view = Mockito.mock(UserPostMVP.View::class.java)

        underTest.attachView(view)
        underTest.getUserPosts(number)
        io.triggerActions()
        ui.triggerActions()

        Mockito.verify(view).displayUserPosts(posts)
    }

    @Test
    fun shouldShowUserPostsFetchError() {
        val error = Exception("Error when fetching user posts.")
        val useCase = Mockito.mock(UserPostUseCase::class.java)
        Mockito.`when`(useCase.getUserPosts(number)).thenReturn(Single.error(error))

        val io = TestScheduler()
        val ui = TestScheduler()
        val underTest = UserPostPresenter(useCase, TestSchedulerProvider(io, ui))

        val view = Mockito.mock(UserPostMVP.View::class.java)

        underTest.attachView(view)
        underTest.getUserPosts(number)
        io.triggerActions()
        ui.triggerActions()

        Mockito.verify(view).displayError(error)
    }

    @Test
    fun shouldCancelFetchUserPostsRequest() {
        val useCase = Mockito.mock(UserPostUseCase::class.java)
        Mockito.`when`(useCase.getUserPosts(number)).thenReturn(Single.just(posts))

        val io = TestScheduler()
        val ui = TestScheduler()
        val underTest = UserPostPresenter(useCase, TestSchedulerProvider(io, ui))

        val view = Mockito.mock(UserPostMVP.View::class.java)

        underTest.attachView(view)
        underTest.getUserPosts(number)
        underTest.cancelUserPostRequest()
        io.triggerActions()
        ui.triggerActions()

        Mockito.verify(view, Mockito.never()).displayUserPosts(KAny.any(emptyList()))
    }

    @Test
    fun shouldNotDisplayUserPostsRequestWhenViewDetaches() {
        val useCase = Mockito.mock(UserPostUseCase::class.java)
        Mockito.`when`(useCase.getUserPosts(number)).thenReturn(Single.just(posts))

        val io = TestScheduler()
        val ui = TestScheduler()
        val underTest = UserPostPresenter(useCase, TestSchedulerProvider(io, ui))

        val view = Mockito.mock(UserPostMVP.View::class.java)

        underTest.attachView(view)
        underTest.getUserPosts(number)
        underTest.detachView()
        io.triggerActions()
        ui.triggerActions()

        Mockito.verify(view, Mockito.never()).displayUserPosts(KAny.any(emptyList()))
    }
}