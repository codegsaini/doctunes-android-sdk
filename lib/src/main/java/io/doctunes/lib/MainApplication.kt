package io.doctunes.lib

import android.app.Application

class MainApplication : Application() {

    companion object {
        lateinit var appModule: Module
    }

    override fun onCreate() {
        super.onCreate()
        appModule = Module
    }
}