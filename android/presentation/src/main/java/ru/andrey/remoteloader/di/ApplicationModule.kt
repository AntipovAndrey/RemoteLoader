package ru.andrey.remoteloader.di

import dagger.Module
import dagger.Provides
import ru.andrey.data.repository.WorkManagerBackgroundWorkRepository
import ru.andrey.domain.repository.BackgroundWorkRepository
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideBackgroundWorkRepository(): BackgroundWorkRepository {
        return WorkManagerBackgroundWorkRepository()
    }
}
