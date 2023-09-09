package com.example.bhagwadgeeta

import android.app.Application
import com.example.bhagwadgeeta.data.pref.Preferences
import dagger.hilt.android.HiltAndroidApp

private var appContext: GeetaApp? = null

@HiltAndroidApp
class GeetaApp : Application() {

    companion object {
        fun getAppContext() = appContext!!
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Preferences.init(this)
    }
}