package ru.andrey.remoteloader.di

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import ru.andrey.common.IO_SCHEDULER
import ru.andrey.data.repository.WorkManagerBackgroundWorkRepository
import ru.andrey.domain.repository.BackgroundWorkRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideBackgroundWorkRepository(): BackgroundWorkRepository {
        return WorkManagerBackgroundWorkRepository()
    }

    @Singleton
    @Provides
    @Named(IO_SCHEDULER)
    fun provideIoScheduler() = Schedulers.io()
}
