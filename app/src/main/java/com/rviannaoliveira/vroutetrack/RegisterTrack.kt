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
    open var latitude: Double = 0.0
    open var longitude: Double = 0.0

}