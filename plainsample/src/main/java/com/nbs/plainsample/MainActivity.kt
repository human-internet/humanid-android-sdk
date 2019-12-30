package com.nbs.plainsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.humanid.auth.HumanIDAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HumanIDAuth.getInstance().requestOTP("62", "81210841382")
                .addOnSuccessListener {
                    Log.d("test", "Success")
                }.addOnFailureListener {
                    Log.d("test", "Fail")
                }


    }
}
