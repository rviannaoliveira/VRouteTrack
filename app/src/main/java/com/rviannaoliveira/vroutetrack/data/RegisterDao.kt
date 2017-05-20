package com.rviannaoliveira.vroutetrack.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rviannaoliveira.vroutetrack.model.RegisterTrack
import io.reactivex.Flowable


/**
 * Criado por rodrigo on 20/05/17.
 */
@Dao
interface RegisterDao {
    @Insert
    fun insert(register : RegisterTrack)
    @Query("SELECT * from registers")
    fun getAllRegisters(): Flowable<List<RegisterTrack>>
    @Query("SELECT * from registers where id = :arg0 LIMIT 1")
    fun findRegisterById(registerId : Int): RegisterTrack
}