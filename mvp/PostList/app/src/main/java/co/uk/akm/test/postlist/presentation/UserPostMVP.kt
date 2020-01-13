package co.uk.akm.test.postlist.presentation

import co.uk.akm.test.postlist.domain.model.UserPost

interface UserPostMVP {

    interface View {

        fun displayUserPosts(userPosts: List<UserPost>)

        fun displayError(error: Throwable)
    }

    interface Presenter {

        fun attachView(view: View)

        fun getUserPosts(number: Int)

        fun cancelUserPostRequest()

        fun detachView()
    }
}