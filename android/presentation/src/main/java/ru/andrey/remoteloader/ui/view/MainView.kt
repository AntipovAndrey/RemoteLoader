package ru.andrey.remoteloader.ui.view

import com.arellomobile.mvp.MvpView
import ru.andrey.domain.model.Command

interface MainView : MvpView {

    fun showCommands(commads: List<Command>)
}
