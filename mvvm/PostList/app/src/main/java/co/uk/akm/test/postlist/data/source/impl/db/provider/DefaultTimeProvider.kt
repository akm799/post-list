package co.uk.akm.test.postlist.data.source.impl.db.provider

class DefaultTimeProvider : TimeProvider {
    override fun now(): Long = System.currentTimeMillis()
}