package com.humanid.lib.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
