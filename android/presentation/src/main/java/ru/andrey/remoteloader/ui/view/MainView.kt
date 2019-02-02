package ru.andrey.remoteloader.ui.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.andrey.domain.model.Command

interface MainView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCommands(commands: List<Command>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: Throwable)
}
