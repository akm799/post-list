package co.uk.akm.test.postlist.presentation.presenter.util

import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.helper.AbstractArgumentMatcher


class UserPostListArgumentMatcher(private val expected: List<UserPost>) : AbstractArgumentMatcher<List<UserPost>>() {

    override fun dummyInstance(): List<UserPost> = emptyList()

    override fun matches(argument: List<UserPost>?): Boolean {
        return (expected == argument)
    }
}