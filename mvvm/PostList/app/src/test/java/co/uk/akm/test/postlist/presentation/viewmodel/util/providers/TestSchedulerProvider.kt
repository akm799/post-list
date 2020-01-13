package co.uk.akm.test.postlist.presentation.viewmodel.util.providers

import co.uk.akm.test.postlist.util.rx.provider.SchedulerProvider
import io.reactivex.Scheduler

class TestSchedulerProvider(private val io: Scheduler, private val ui: Scheduler) : SchedulerProvider {

    override fun io(): Scheduler = io

    override fun ui(): Scheduler = ui
}