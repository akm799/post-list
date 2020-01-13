package co.uk.akm.test.postlist.presentation.ui.list


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.uk.akm.test.postlist.R
import co.uk.akm.test.postlist.domain.model.UserPost
import kotlinx.android.synthetic.main.item_user_post.view.*

class UserPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindUserPost(userPost: UserPost, position: Int) {
        with(itemView) {
            postNumber.text = resources.getString(R.string.post_number, position + 1)
            postTitle.text = userPost.title
            postUserName.text = resources.getString(R.string.user_name, userPost.username)
        }
    }
}