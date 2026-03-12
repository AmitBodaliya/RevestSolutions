package com.abapp.revestsolutions

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import com.abapp.revestsolutions.di.initKoin

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initContext(this)


        //di
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }

    }

}
