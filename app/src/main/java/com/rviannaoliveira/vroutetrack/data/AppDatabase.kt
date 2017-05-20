package com.rviannaoliveira.vroutetrack.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rviannaoliveira.vroutetrack.model.RegisterTrack




/**
 * Criado por rodrigo on 20/05/17.
 */
@Database(entities = arrayOf(RegisterTrack::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registerDao(): RegisterDao
}