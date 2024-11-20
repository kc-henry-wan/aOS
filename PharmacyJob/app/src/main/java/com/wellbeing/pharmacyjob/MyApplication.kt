package com.wellbeing.pharmacyjob

import android.app.Application
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        RetrofitInstance.init(this)
        AppLogger.d("MyApplication", "MyApplication: RetrofitInstance init")

    }
}
