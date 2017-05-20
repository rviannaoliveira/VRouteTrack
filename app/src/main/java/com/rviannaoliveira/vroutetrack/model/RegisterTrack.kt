package com.rviannaoliveira.vroutetrack.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Criado por rodrigo on 17/05/17.
 */
@Entity(tableName = "registers")
open class RegisterTrack{
    @PrimaryKey(autoGenerate = true)
    open var id: Int? = null
    @ColumnInfo(name = "addresses")
    open var address: String? = null
    open var latitude: Double? = null
    open var longitude: Double? = null
    open var timeStamp : Long? = null

    fun getTimeFormat() : String {
        this.timeStamp?.let {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date()
            date.time = it
            return sdf.format(date)
        }
        return ""
    }
}