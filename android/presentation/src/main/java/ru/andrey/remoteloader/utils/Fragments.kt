package ru.andrey.remoteloader.utils

import android.support.v4.app.Fragment
import ru.andrey.remoteloader.App

val Fragment.app: App
    get() = (activity!!.application as App)
