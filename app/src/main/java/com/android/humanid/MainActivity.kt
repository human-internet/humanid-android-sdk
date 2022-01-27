package com.android.humanid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.humanid.lib.presentation.HumanIdSDK

class MainActivity : AppCompatActivity() {
    private val humanIdSDK: HumanIdSDK? by lazy {
        HumanIdSDK.Builder()
            .withContext(this)
            .addClientId(getString(R.string.client_id))
            .addClientSecret(getString(R.string.client_secret))
            .setPriorityCountryCodes(arrayOf("US", "FR", "JP", "ID"))
            .setDefaultLanguage("en")
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            humanIdSDK?.login()
        }
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val exchangeToken = intent?.data?.let { humanIdSDK?.parse(it) }
        print("Exchange Token : $exchangeToken")
    }
}