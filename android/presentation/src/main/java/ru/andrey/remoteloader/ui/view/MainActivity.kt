package ru.andrey.remoteloader.ui.view

import ru.andrey.remoteloader.ui.SingleFragmentActivity

class MainActivity : SingleFragmentActivity() {

    override fun createFragment() = MainFragment()
}
