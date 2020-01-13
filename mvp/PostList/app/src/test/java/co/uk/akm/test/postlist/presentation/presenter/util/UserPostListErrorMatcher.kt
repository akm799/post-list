package co.uk.akm.test.postlist.presentation.presenter.util

import co.uk.akm.test.postlist.helper.AbstractArgumentMatcher

class UserPostListErrorMatcher(private val error: Throwable) : AbstractArgumentMatcher<Throwable>() {

    override fun dummyInstance(): Throwable = Exception()

    override fun matches(argument: Throwable?): Boolean {
        return (error == argument)
    }
}