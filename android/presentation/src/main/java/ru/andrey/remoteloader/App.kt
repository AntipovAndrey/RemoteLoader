package ru.andrey.remoteloader

import android.app.Application
import ru.andrey.remoteloader.di.ApplicationComponent
import ru.andrey.remoteloader.di.DaggerApplicationComponent
import ru.andrey.remoteloader.di.remote.DaggerRemoteLoaderComponent
import ru.andrey.remoteloader.di.remote.RemoteLoaderComponent

class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .context(this)
            .build()
    }

    val remoteLoaderComponent: RemoteLoaderComponent by lazy {
        DaggerRemoteLoaderComponent.builder()
            .applicationComponent(appComponent)
            .build()
    }
}
