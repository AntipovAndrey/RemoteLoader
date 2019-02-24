package ru.andrey.remoteloader.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.andrey.domain.repository.BackgroundWorkRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun backgroundWorkRepository(): BackgroundWorkRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }
}
