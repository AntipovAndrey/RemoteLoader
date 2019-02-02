package ru.andrey.remoteloader.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.andrey.remoteloader.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        if (savedInstanceState != null) {
            return
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, createFragment())
            .commit()
    }
}
