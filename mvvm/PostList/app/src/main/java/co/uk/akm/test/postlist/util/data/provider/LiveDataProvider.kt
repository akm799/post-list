package co.uk.akm.test.postlist.util.data.provider

import androidx.lifecycle.MutableLiveData
import co.uk.akm.test.postlist.util.data.CallResult

interface LiveDataProvider {

    fun <T> liveDataInstance(): MutableLiveData<CallResult<T>>
}