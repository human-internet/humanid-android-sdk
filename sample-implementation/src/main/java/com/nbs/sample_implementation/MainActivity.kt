package com.nbs.sample_implementation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOpenAuthLib.setOnClickListener {
            HumanIDUI.instance.verifyLogin(supportFragmentManager)
        }
    }

    override fun onDestroy() {
        HumanIDUI.instance.closeDialog()
        super.onDestroy()
    }
}
