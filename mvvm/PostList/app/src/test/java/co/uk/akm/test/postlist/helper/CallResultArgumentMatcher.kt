package co.uk.akm.test.postlist.helper

import co.uk.akm.test.postlist.util.data.CallResult

abstract class CallResultArgumentMatcher<T> : AbstractArgumentMatcher<CallResult<T>>() {

    final override fun matches(argument: CallResult<T>?): Boolean {
        if (argument == null) {
            return false
        }

        if (argument.hasError()) {
            return false
        }

        return match(argument.result())
    }

    abstract fun match(result: T): Boolean
}