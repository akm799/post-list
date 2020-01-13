package co.uk.akm.test.postlist.helper

import co.uk.akm.test.postlist.util.data.CallResult

abstract class CallResultErrorMatcher<T>(private val error: Throwable) : AbstractArgumentMatcher<CallResult<T>>() {

    final override fun matches(argument: CallResult<T>?): Boolean {
        if (argument == null) {
            return false
        }

        if (argument.hasResult()) {
            return false
        }

        return match(argument.error())
    }

    fun match(error: Throwable): Boolean {
        return (this.error == error)
    }
}