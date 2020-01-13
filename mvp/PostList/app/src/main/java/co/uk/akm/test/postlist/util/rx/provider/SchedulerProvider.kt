package co.uk.akm.test.postlist.util.rx.provider

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}