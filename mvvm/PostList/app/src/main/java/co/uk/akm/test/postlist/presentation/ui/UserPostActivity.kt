package co.uk.akm.test.postlist.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.akm.test.postlist.BuildConfig
import co.uk.akm.test.postlist.R
import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.presentation.ui.list.UserPostAdapter
import co.uk.akm.test.postlist.presentation.viewmodel.UserPostViewModel
import co.uk.akm.test.postlist.util.data.CallResult
import co.uk.akm.test.postlist.util.error.findErrorResId
import kotlinx.android.synthetic.main.activity_user_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPostActivity : AppCompatActivity(), Observer<CallResult<List<UserPost>>> {
    private val viewModel: UserPostViewModel by viewModel()

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

        fetchPosts()
    }

    private fun fetchPosts() {
        userPostRequestStatus.showProgress()
        viewModel.getUserPosts(BuildConfig.NUMBER_OF_POSTS).observe(this, this)
    }

    override fun onChanged(t: CallResult<List<UserPost>>?) {
        t?.also { displayCallResult(it) }
    }

    private fun displayCallResult(callResult: CallResult<List<UserPost>>) {
        if (callResult.hasResult()) {
            displayUserPosts(callResult.result())
        } else {
            displayError(callResult.error())
        }
    }

    private fun displayUserPosts(userPosts: List<UserPost>) {
        userPostRequestStatus.showSuccess()
        (userPostList.adapter as UserPostAdapter).submitList(userPosts)
    }

    private fun displayError(error: Throwable) {
        userPostRequestStatus.showError(findErrorResId(error))
    }
}
