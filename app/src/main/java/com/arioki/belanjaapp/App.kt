package com.arioki.belanjaapp

import android.app.Application
import com.arioki.belanjaapp.remote.BelanjaApi
import com.arioki.belanjaapp.repository.ProductRepository
import java.lang.RuntimeException

class App : Application(){
    val repository: ProductRepository by lazy {
        ProductRepository(BelanjaApi.create())
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object{
        @Volatile
        private var INSTANCE:App? = null
        val instances: App
        get(){
            if (INSTANCE == null){
                synchronized(App::class.java){
                    if (INSTANCE==null){
                        throw RuntimeException("Something went wrong!")
                    }
                }
            }
            return INSTANCE!!
        }
    }
}