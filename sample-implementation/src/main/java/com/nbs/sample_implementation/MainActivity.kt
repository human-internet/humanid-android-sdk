package com.nbs.sample_implementation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nbs.humanidui.presentation.main.MainDialogFragment
import com.nbs.humanidui.presentation.welcome.WelcomeDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WelcomeDialogFragment.OnWelcomeDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WelcomeDialogFragment.listener = this
        showDialogWelcome()

        btnOpenAuthLib.setOnClickListener {
            showDialogWelcome()
        }
    }

    private fun showDialogWelcome(){
        val welcomeDialogFragment = WelcomeDialogFragment.newInstance()
        welcomeDialogFragment.show(supportFragmentManager, "test")
    }

    private fun showDialogMain(){
        val mainDialogFragment = MainDialogFragment.newInstance()
        mainDialogFragment.show(supportFragmentManager, "test")
    }

    override fun onButtonContinueClicked() {
        showDialogMain()
    }
}
