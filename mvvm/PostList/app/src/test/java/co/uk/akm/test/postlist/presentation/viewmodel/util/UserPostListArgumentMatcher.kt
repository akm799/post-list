package co.uk.akm.test.postlist.presentation.viewmodel.util

import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.helper.CallResultArgumentMatcher
import co.uk.akm.test.postlist.util.data.CallResult


class UserPostListArgumentMatcher(private val expected: List<UserPost>) : CallResultArgumentMatcher<List<UserPost>>() {

    override fun match(actual: List<UserPost>): Boolean {
        return expected.equals(actual)
    }

    override fun dummyInstance(): CallResult<List<UserPost>> = CallResult(emptyList())
}