package co.uk.akm.test.postlist.presentation.viewmodel.util

import co.uk.akm.test.postlist.domain.model.UserPost
import co.uk.akm.test.postlist.helper.CallResultErrorMatcher
import co.uk.akm.test.postlist.util.data.CallResult

class UserPostListErrorMatcher(error: Throwable) : CallResultErrorMatcher<List<UserPost>>(error) {
    override fun dummyInstance(): CallResult<List<UserPost>> = CallResult(emptyList())
}