package ru.andrey.remoteloader.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import ru.andrey.remoteloader.ui.SingleFragmentActivity


class MainActivity : SingleFragmentActivity() {

    override fun createFragment() = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askReadPermission()
    }

    private fun askReadPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return
        }
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return
        }
        ActivityCompat.requestPermissions(this, arrayOf(permission), 3)
    }
}
