package ru.andrey.remoteloader.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.reactivex.Scheduler
import ru.andrey.common.IO_SCHEDULER
import ru.andrey.domain.repository.BackgroundWorkRepository
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun backgroundWorkRepository(): BackgroundWorkRepository

    @Named(IO_SCHEDULER)
    fun ioScheduler(): Scheduler

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }
}
