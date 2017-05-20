package com.rviannaoliveira.vroutetrack

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Criado por rodrigo on 17/05/17.
 */
open class RegisterTrack : RealmObject() {
    @PrimaryKey
    open var id: Int? = null
    open var address: String? = null
    open var latitude: Double? = null
    open var longitude: Double? = null
    open var timeStamp : Long? = null

    fun getTimeFormat() : String {
        timeStamp?.let {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date()
            date.time = it
            return sdf.format(date)
        }
        return ""
    }
}