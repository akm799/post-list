package co.uk.akm.test.postlist.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.akm.test.postlist.BuildConfig
import co.uk.akm.test.postlist.R
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.presentation.UserPostMVP
import co.uk.akm.test.postlist.presentation.ui.list.UserPostAdapter
import co.uk.akm.test.postlist.util.error.findErrorResId
import kotlinx.android.synthetic.main.activity_user_post.*
import org.koin.android.ext.android.inject

class UserPostActivity : AppCompatActivity(), UserPostMVP.View {
    private val presenter: UserPostMVP.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_post)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(userPostList) {
            layoutManager = LinearLayoutManager(this@UserPostActivity)
            adapter = UserPostAdapter()
        }
    }

    override fun onResume() {
        super.onResume()

        presenter.attachView(this)
        fetchPosts()
    }

    override fun onPause() {
        super.onPause()

        with(presenter) {
            cancelUserPostRequest()
            detachView()
        }
    }

    private fun fetchPosts() {
        userPostRequestStatus.showProgress()
        presenter.getUserPosts(BuildConfig.NUMBER_OF_POSTS)
    }

    override fun displayUserPosts(userPosts: List<UserPost>) {
        userPostRequestStatus.showSuccess()
        (userPostList.adapter as UserPostAdapter).submitList(userPosts)
    }

    override fun displayError(error: Throwable) {
        userPostRequestStatus.showError(findErrorResId(error))
    }
}
