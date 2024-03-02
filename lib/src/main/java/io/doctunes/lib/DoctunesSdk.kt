package io.doctunes.lib

import android.content.Context
import android.util.Log
import java.lang.reflect.Field
import java.util.concurrent.atomic.AtomicBoolean

object DoctunesSdk {
    private lateinit var applicationContext: Context
    private val sdkInitialized = AtomicBoolean(false)

    @Synchronized
    @JvmStatic
    fun initializeSdk(applicationContext: Context) {
        initializeSdk(applicationContext, null)

    }

    @Synchronized
    @JvmStatic
    fun initializeSdk(applicationContext: Context, callback: InitializeCallback?) {
        if (sdkInitialized.get()) {
            callback?.onInitialized()
            return
        }
        DoctunesSdk.applicationContext = applicationContext.applicationContext
        sdkInitialized.set(true)
        callback?.onInitialized()
    }

    fun f() {

    }

    fun interface InitializeCallback {
        fun onInitialized()
    }

    fun syncString(stringFields: Array<Field> ) {
        stringFields.map {
            Log.d("TTTG", applicationContext.resources.getString(it.getInt(applicationContext.resources)))
        }
        val viewModel = MainViewModel(Application.appModule.repository)
        viewModel.sync()

    }
}