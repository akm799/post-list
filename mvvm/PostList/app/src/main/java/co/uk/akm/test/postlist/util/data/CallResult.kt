package co.uk.akm.test.postlist.util.data

class CallResult<T> {
    private var result: T? = null
    private var error: Throwable? = null

    constructor(result: T) {
        this.result = result
        this.error = null
    }

    constructor(error: Throwable) {
        this.result = null
        this.error = error
    }

    fun hasResult() = (result != null)

    fun hasError() = (error != null)

    fun result(): T = result ?: throw IllegalStateException("Please ensure that hasResult() returns true and/or hasError() returns false before calling this method.")

    fun error(): Throwable = error  ?: throw IllegalStateException("Please ensure that hasError() returns true and/or hasResult() returns false before calling this method.")
}