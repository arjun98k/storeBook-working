package com.example.mybookstore

import android.app.Application
import com.example.mybookstore.data.local.MyObjectBox
import io.objectbox.BoxStore

class MyBookStoreApp : Application() {

    companion object {
        lateinit var boxStore: BoxStore
            private set
    }

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder()
            .androidContext(this)
            .build()
    }
}
