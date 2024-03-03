package io.doctunes.lib

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.reflect.Field
import java.util.concurrent.atomic.AtomicBoolean

private val Context.preferencesDataStore:
        DataStore<Preferences> by preferencesDataStore("doctunes_datastore")

private val STRING_RESOURCES_VERSION = longPreferencesKey("string_Resources_version")

object DoctunesSdk {
    private lateinit var applicationContext: Context
    private lateinit var resources: Resources
    private lateinit var preference: DataStore<Preferences>
    private val sdkInitialized = AtomicBoolean(false)

    private val TAG = javaClass.canonicalName

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
        resources = applicationContext.resources
        preference = applicationContext.preferencesDataStore
        sdkInitialized.set(true)
        callback?.onInitialized()
    }

    fun interface InitializeCallback {
        fun onInitialized()
    }

    fun interface FunctionCallback {
        fun onInform(string: String)
    }

    fun syncString(stringFields: Array<Field>, callback: FunctionCallback? ) {

        callback?.onInform("Checking string resources version...")
        val versionField = stringFields.find { it.name.equals("doctunes_translate_version") }
        if (versionField == null) {
            callback?.onInform("`doctunes_translate_version` not found in string resources")
            return
        }

        val versionInt = resources.getString(versionField.getInt(resources)).toIntOrNull()
        if (versionInt == null) {
            callback?.onInform("Unable to get `doctunes_translate_version`")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val flow = preference.data.map {
                it[STRING_RESOURCES_VERSION]
            }
            val previousVersion = flow.firstOrNull() ?: -1
            if (versionInt > previousVersion) {
                callback?.onInform("Synchronizing your string resources with Doctunes database...")
                MainApplication.appModule.repository.syncStrings { message, exception ->
                    if (exception != null) {
                        Log.d(TAG, "syncStrings: ${exception.message}")
                    }
                    Log.d(TAG, "syncStrings: $message")
                }
            }
            callback?.onInform("String resources version $versionInt is already synchronized")
        }
    }
}