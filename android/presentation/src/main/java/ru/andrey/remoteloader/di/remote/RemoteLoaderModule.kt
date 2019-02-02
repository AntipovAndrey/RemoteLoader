package ru.andrey.remoteloader.di.remote

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.andrey.data.api.RemoteLoaderApi
import ru.andrey.data.repository.RemoteCommandRepositoryImpl
import ru.andrey.domain.interactor.RemoteCommandInteractor
import ru.andrey.domain.interactor.RemoteCommandInteractorImpl
import ru.andrey.domain.repository.RemoteCommandRepository
import ru.andrey.remoteloader.di.scope.Feature

@Module
class RemoteLoaderModule {

    @Feature
    @Provides
    fun provideRemoteCommandInteractor(repository: RemoteCommandRepository): RemoteCommandInteractor {
        return RemoteCommandInteractorImpl(Schedulers.io(), repository)
    }

    @Feature
    @Provides
    fun provideRemoteCommandRepository(context: Context, retrofit: Retrofit): RemoteCommandRepository {
        return RemoteCommandRepositoryImpl(context, retrofit.create(RemoteLoaderApi::class.java))
    }
}
