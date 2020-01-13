package co.uk.akm.test.postlist.presentation.ui.list

import androidx.recyclerview.widget.DiffUtil
import co.uk.akm.test.postlist.domain.model.UserPost

val USER_POST_DIFF = object : DiffUtil.ItemCallback<UserPost>() {

    override fun areItemsTheSame(oldItem: UserPost, newItem: UserPost): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: UserPost, newItem: UserPost): Boolean {
        return (oldItem == newItem)
    }
}