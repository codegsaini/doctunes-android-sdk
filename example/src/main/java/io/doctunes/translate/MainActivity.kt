package io.doctunes.translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.doctunes.lib.DoctunesSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DoctunesSdk.initializeSdk(applicationContext)
        val stringResources = R.string::class.java.fields

        DoctunesSdk.syncString(stringResources) {
            Log.d("TTTG", "onCreate: $it")
        }
    }
}