package hs.project.mvvmexample

import android.app.Application

class MyApp: Application() {

    companion object {

        lateinit var INSTANCE: MyApp
            private set
        var isForeground = false
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}