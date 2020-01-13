package co.uk.akm.test.postlist.util.data.provider

import androidx.lifecycle.MutableLiveData
import co.uk.akm.test.postlist.util.data.CallResult

class DefaultLiveDataProvider : LiveDataProvider {

    override fun <T> liveDataInstance(): MutableLiveData<CallResult<T>> = MutableLiveData()
}