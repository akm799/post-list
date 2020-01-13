package co.uk.akm.test.postlist.domain.extensions

import co.uk.akm.test.postlist.domain.model.Post
import co.uk.akm.test.postlist.domain.model.User
import co.uk.akm.test.postlist.domain.model.UserPost

infix fun Post.with(user: User): UserPost = UserPost(id, title, user.name)