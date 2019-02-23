package ru.andrey.remoteloader.ui.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.andrey.domain.interactor.RemoteCommandInteractor
import ru.andrey.remoteloader.ui.view.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val interactor: RemoteCommandInteractor
) : MvpPresenter<MainView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        interactor.observeProcessedCommands()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ viewState.showCommands(it) }, { viewState.showError(it) })
            .also { disposables.add(it) }
    }

    override fun onDestroy() {
        disposables.dispose()
    }
}