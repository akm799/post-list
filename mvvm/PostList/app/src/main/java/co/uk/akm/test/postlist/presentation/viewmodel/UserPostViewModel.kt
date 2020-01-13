package co.uk.akm.test.postlist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.uk.akm.test.postlist.domain.interactor.UserPostUseCase
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.util.data.CallResult
import co.uk.akm.test.postlist.util.data.provider.DefaultLiveDataProvider
import co.uk.akm.test.postlist.util.data.provider.LiveDataProvider
import co.uk.akm.test.postlist.util.rx.observer.SingleLiveDataObserver
import co.uk.akm.test.postlist.util.rx.provider.DefaultSchedulerProvider
import co.uk.akm.test.postlist.util.rx.provider.SchedulerProvider
import io.reactivex.Single

/**
 * The providers are not needed but are used for unit testing purposes. In unit tests we do not use
 * the default providers seen here, but use custom providers that return instances suitable for unit
 * testing purposes.
 */
class UserPostViewModel(
    private val useCase: UserPostUseCase,
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider(),
    private val liveDataProvider: LiveDataProvider = DefaultLiveDataProvider()
) : ViewModel() {

    fun getUserPosts(number: Int): LiveData<CallResult<List<UserPost>>> {
        return liveData { useCase.getUserPosts(number) }
    }

    private fun <T> liveData(getData: () -> Single<T>): LiveData<CallResult<T>> {
        val liveData = liveDataProvider.liveDataInstance<T>()
        val rxObserver = SingleLiveDataObserver(liveData)

        getData.invoke()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(rxObserver)

        return liveData
    }
}