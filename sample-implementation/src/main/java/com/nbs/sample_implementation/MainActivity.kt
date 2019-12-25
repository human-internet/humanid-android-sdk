package com.nbs.sample_implementation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nbs.humanidui.presentation.HumanIDUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var humanIDUI: HumanIDUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        humanIDUI = HumanIDUI(supportFragmentManager)

        btnOpenAuthLib.setOnClickListener {
            humanIDUI.verifyLogin()
        }
    }

    override fun onDestroy() {
        humanIDUI.closeDialog()
        super.onDestroy()
    }
}
