package com.nbs.sample_implementation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nbs.humanidui.presentation.HumanIdUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var humanIdUI: HumanIdUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        humanIdUI = HumanIdUI(supportFragmentManager)

        btnOpenAuthLib.setOnClickListener {
            humanIdUI.verifyLogin()
        }
    }

    override fun onDestroy() {
        humanIdUI.closeDialog()
        super.onDestroy()
    }
}
