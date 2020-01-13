package co.uk.akm.test.postlist.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.uk.akm.test.postlist.R
import co.uk.akm.test.postlist.domain.model.UserPost

class UserPostAdapter : ListAdapter<UserPost, UserPostViewHolder>(USER_POST_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_post, parent, false)

        return UserPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        holder.bindUserPost(getItem(position), position)
    }
}