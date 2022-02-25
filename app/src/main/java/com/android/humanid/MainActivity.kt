package com.android.humanid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.humanid.lib.presentation.HumanIdSDK
import com.humanid.lib.util.CountryCode
import com.humanid.lib.util.SupportedLanguage

class MainActivity : AppCompatActivity() {
    private val humanIdSDK: HumanIdSDK? by lazy {
        HumanIdSDK.Builder()
            .withActivity(this)
            .addClientId(getString(R.string.client_id))
            .addClientSecret(getString(R.string.client_secret))
            .setDefaultLanguage(SupportedLanguage.FRENCH)
            .setPriorityCountryCodes(arrayOf(
                CountryCode.UNITED_STATES,
                CountryCode.FRANCE,
                CountryCode.JAPAN,
                CountryCode.INDONESIA
            )).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            humanIdSDK?.login()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val exchangeToken = humanIdSDK?.getHumanIdExchangeToken(requestCode, resultCode, data)
        Log.d("Exchange Token", exchangeToken.orEmpty())
        findViewById<TextView>(R.id.tvToken).text = exchangeToken.orEmpty()
    }
}