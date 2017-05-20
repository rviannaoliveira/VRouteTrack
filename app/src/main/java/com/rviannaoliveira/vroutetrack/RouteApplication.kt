package com.rviannaoliveira.vroutetrack

import android.app.Application
import com.rviannaoliveira.vroutetrack.data.RoomDatabase

/**
 * Criado por rodrigo on 18/05/17.
 */
class RouteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRoom()
    }

    private fun initRoom() {
        RoomDatabase.init(this)
    }


}