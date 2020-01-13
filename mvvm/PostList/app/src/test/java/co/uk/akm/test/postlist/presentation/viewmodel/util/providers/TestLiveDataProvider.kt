package co.uk.akm.test.postlist.presentation.viewmodel.util.providers

import androidx.lifecycle.MutableLiveData
import co.uk.akm.test.postlist.util.data.CallResult
import co.uk.akm.test.postlist.util.data.provider.LiveDataProvider

class TestLiveDataProvider<I>(private val liveData: MutableLiveData<CallResult<I>>) : LiveDataProvider {
    override fun <T> liveDataInstance(): MutableLiveData<CallResult<T>> = liveData as MutableLiveData<CallResult<T>>
}