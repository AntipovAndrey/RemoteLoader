package ru.andrey.remoteloader.ui.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_main.*
import ru.andrey.domain.model.Command
import ru.andrey.remoteloader.R
import ru.andrey.remoteloader.ui.presenter.MainPresenter
import ru.andrey.remoteloader.utils.app

class MainFragment : MvpAppCompatFragment(), MainView {

    private lateinit var mainAdapter: MainAdapter

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    internal fun providePresenter() = app.remoteLoaderComponent.presenter().mainPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainAdapter = MainAdapter()
        with(recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
    }

    override fun showCommands(commands: List<Command>) {
        mainAdapter.submitList(commands)
        recycler.visibility = View.VISIBLE
        errorTextView.visibility = View.INVISIBLE
    }

    override fun showError(error: Throwable) {
        errorTextView.text = "${error.javaClass.simpleName} \n ${error.message}"
        recycler.visibility = View.INVISIBLE
        errorTextView.visibility = View.VISIBLE
    }
}