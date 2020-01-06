package com.nbs.sample_implementation

import androidx.multidex.MultiDexApplication
import com.nbs.humanidui.util.ContextProvider

class BaseApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.initialize(this)
    }
}