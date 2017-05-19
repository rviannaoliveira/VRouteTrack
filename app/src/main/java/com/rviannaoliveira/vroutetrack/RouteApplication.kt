package com.rviannaoliveira.vroutetrack

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Criado por rodrigo on 18/05/17.
 */
class RouteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }


    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .schemaVersion(0)
                .build()
        Realm.setDefaultConfiguration(config)
    }
}