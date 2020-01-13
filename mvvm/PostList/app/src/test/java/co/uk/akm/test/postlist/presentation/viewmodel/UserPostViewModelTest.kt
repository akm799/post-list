package co.uk.akm.test.postlist.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import co.uk.akm.test.postlist.domain.interactor.UserPostUseCase
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.presentation.viewmodel.util.UserPostListArgumentMatcher
import co.uk.akm.test.postlist.presentation.viewmodel.util.UserPostListErrorMatcher
import co.uk.akm.test.postlist.presentation.viewmodel.util.providers.TestLiveDataProvider
import co.uk.akm.test.postlist.presentation.viewmodel.util.providers.TestSchedulerProvider
import co.uk.akm.test.postlist.util.data.CallResult
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class UserPostViewModelTest {

    @Test
    fun shouldSetFetchedUserPosts() {
        val number = 1
        val posts = listOf(UserPost(42, "Post Title", "john"))
        val useCase = Mockito.mock(UserPostUseCase::class.java)
        Mockito.`when`(useCase.getUserPosts(number)).thenReturn(Single.just(posts))

        val io = TestScheduler()
        val ui = TestScheduler()
        val mockLiveData = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<CallResult<List<UserPost>>>

        val underTest = UserPostViewModel(useCase,
            TestSchedulerProvider(io, ui),
            TestLiveDataProvider(mockLiveData)
        )

        val liveData = underTest.getUserPosts(number)
        io.triggerActions()
        ui.triggerActions()
        Assert.assertEquals(mockLiveData, liveData)
        Mockito.verify(mockLiveData).setValue(UserPostListArgumentMatcher(posts).mockArgument())
    }

    @Test
    fun shouldSetUserPostsFetchError() {
        val number = 1
        val error = Exception("Error when fetching the user posts.")
        val useCase = Mockito.mock(UserPostUseCase::class.java)
        Mockito.`when`(useCase.getUserPosts(number)).thenReturn(Single.error(error))

        val io = TestScheduler()
        val ui = TestScheduler()
        val mockLiveData = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<CallResult<List<UserPost>>>

        val underTest = UserPostViewModel(useCase,
            TestSchedulerProvider(io, ui),
            TestLiveDataProvider(mockLiveData)
        )

        val liveData = underTest.getUserPosts(number)
        io.triggerActions()
        ui.triggerActions()
        Assert.assertEquals(mockLiveData, liveData)
        Mockito.verify(mockLiveData).setValue(UserPostListErrorMatcher(error).mockArgument())
    }
}