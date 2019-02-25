package ru.andrey.remoteloader.di.remote

import dagger.Binds
import dagger.Module
import ru.andrey.data.repository.DeviceRepositoryImpl
import ru.andrey.data.repository.RemoteCommandRepositoryImpl
import ru.andrey.domain.interactor.BackgroundCommandInteractor
import ru.andrey.domain.interactor.BackgroundCommandInteractorImpl
import ru.andrey.domain.interactor.RemoteCommandInteractor
import ru.andrey.domain.interactor.RemoteCommandInteractorImpl
import ru.andrey.domain.repository.DeviceRepository
import ru.andrey.domain.repository.RemoteCommandRepository
import ru.andrey.remoteloader.di.scope.Feature

@Module
abstract class RemoteLoaderModule {

    @Feature
    @Binds
    abstract fun provideRemoteCommandInteractor(
        impl: RemoteCommandInteractorImpl
    ): RemoteCommandInteractor

    @Feature
    @Binds
    abstract fun provideBackgroundCommandInteractor(
        impl: BackgroundCommandInteractorImpl
    ): BackgroundCommandInteractor

    @Feature
    @Binds
    abstract fun provideDeviceRepository(
        impl: DeviceRepositoryImpl
    ): DeviceRepository

    @Feature
    @Binds
    abstract fun provideRemoteCommandRepository(
        impl: RemoteCommandRepositoryImpl
    ): RemoteCommandRepository
}
