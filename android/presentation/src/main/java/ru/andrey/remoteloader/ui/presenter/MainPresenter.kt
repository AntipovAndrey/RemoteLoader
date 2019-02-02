package ru.andrey.remoteloader.ui.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.andrey.remoteloader.ui.view.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor() : MvpPresenter<MainView>() {
}