package com.teknolojipiri.contactsgenerator

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ContactsGenerator : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: Context
    }
}