package com.rviannaoliveira.vroutetrack

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

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

}