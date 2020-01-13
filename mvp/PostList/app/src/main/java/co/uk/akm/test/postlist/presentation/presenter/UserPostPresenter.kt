package co.uk.akm.test.postlist.presentation.presenter

import co.uk.akm.test.postlist.domain.interactor.UserPostUseCase
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.presentation.UserPostMVP
import co.uk.akm.test.postlist.util.rx.provider.DefaultSchedulerProvider
import co.uk.akm.test.postlist.util.rx.provider.SchedulerProvider
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class UserPostPresenter(
    private val useCase: UserPostUseCase,
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : UserPostMVP.Presenter {

    private var view: UserPostMVP.View? = null
    private var userPostRequest: Disposable? = null

    override fun attachView(view: UserPostMVP.View) {
        this.view = view
    }

    override fun getUserPosts(number: Int) {
        useCase.getUserPosts(number)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(UserPostObserver())
    }

    override fun cancelUserPostRequest() {
        userPostRequest?.dispose()
        userPostRequest = null
    }

    override fun detachView() {
        view = null
    }

    private inner class UserPostObserver : SingleObserver<List<UserPost>> {

        override fun onSubscribe(d: Disposable) {
            userPostRequest = d
        }

        override fun onSuccess(t: List<UserPost>) {
            userPostRequest = null
            view?.displayUserPosts(t)
        }

        override fun onError(e: Throwable) {
            userPostRequest = null
            view?.displayError(e)
        }
    }
}