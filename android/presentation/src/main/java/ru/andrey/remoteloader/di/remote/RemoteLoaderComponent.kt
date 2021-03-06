package ru.andrey.remoteloader.di.remote

import dagger.Component
import dagger.Subcomponent
import ru.andrey.domain.interactor.BackgroundCommandInteractor
import ru.andrey.remoteloader.di.ApplicationComponent
import ru.andrey.remoteloader.di.scope.Feature
import ru.andrey.remoteloader.di.scope.Screen
import ru.andrey.remoteloader.ui.presenter.MainPresenter

@Feature
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [RemoteLoaderModule::class, RemoteLoaderDataModule::class]
)
interface RemoteLoaderComponent {

    fun presenterComponent(): PresenterComponent

    fun backgroundCommands() : BackgroundCommandInteractor

    @Screen
    @Subcomponent
    interface PresenterComponent {

        fun mainPresenter(): MainPresenter
    }
}
