package io.doctunes.translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.doctunes.lib.DoctunesSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DoctunesSdk.initializeSdk(applicationContext)
        val d = R.string::class.java.fields
        DoctunesSdk.syncString(d)

    }
}