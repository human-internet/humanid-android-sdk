package com.humanid.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.humanid.humanidui.presentation.LoginCallback
import com.humanid.humanidui.presentation.LoginManager
import com.humanid.humanidui.util.extensions.showToast
import kotlinx.android.synthetic.main.activity_main.btnLogin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            loginHumanID()
        }
    }

    private fun loginHumanID() {
        LoginManager.getInstance(this).registerCallback(object : LoginCallback {
            override fun onCancel() {
                showToast("Login Cancelled")
            }

            override fun onSuccess(exchangeToken: String) {
                Log.d("ExchangeToken", exchangeToken)
                //todo send the exchangeToken to your server
                btnLogin.isEnabled = false
                showToast("Login humanID succeeed")
            }

            override fun onError(errorMessage: String) {
                showToast("Login humanID Failed : $errorMessage")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LoginManager.getInstance(this).onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
