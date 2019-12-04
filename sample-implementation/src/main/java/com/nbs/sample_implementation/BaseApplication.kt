package com.nbs.sample_implementation

import androidx.multidex.MultiDexApplication

class BaseApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}