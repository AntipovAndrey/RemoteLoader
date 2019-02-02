package ru.andrey.remoteloader.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.andrey.remoteloader.R
import ru.andrey.remoteloader.ui.presenter.MainPresenter
import ru.andrey.remoteloader.utils.app

class MainFragment : MvpAppCompatFragment(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    internal fun providePresenter() = app.remoteLoaderComponent.presenter().mainPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)


}