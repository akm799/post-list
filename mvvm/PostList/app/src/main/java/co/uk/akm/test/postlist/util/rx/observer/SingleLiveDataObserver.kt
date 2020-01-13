package co.uk.akm.test.postlist.util.rx.observer

import androidx.lifecycle.MutableLiveData
import co.uk.akm.test.postlist.util.data.CallResult
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class SingleLiveDataObserver<T>(
    private val liveData: MutableLiveData<CallResult<T>>
) : SingleObserver<T>, DisposableObserver {

    private var current: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        current = d
    }

    override fun onSuccess(t: T) {
        current = null
        liveData.value = CallResult(t)
    }

    override fun onError(e: Throwable) {
        current = null
        liveData.value = CallResult(e)
    }

    override fun dispose() {
        current?.dispose()
    }
}