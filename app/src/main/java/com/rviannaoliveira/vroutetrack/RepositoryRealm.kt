package com.rviannaoliveira.vroutetrack

import io.realm.Realm

/**
 * Criado por rodrigo on 18/05/17.
 */


object RepositoryRealm{
    private val realm = Realm.getDefaultInstance()


    fun getAllRegister(): List<RegisterTrack> {
        return realm.where(RegisterTrack::class.java).findAll().toList()
    }
    fun insert(register: RegisterTrack) {
        if (register.id == null) {
            register.id = this.getNextKey()
        }
        realm.executeTransactionAsync { realm1 -> realm1.insertOrUpdate(register) }
    }

    private fun getNextKey(): Int {
        try {
            return realm.where(RegisterTrack::class.java).max("id").toInt() + 1
        } catch (e: Exception) {
            return 1
        }

    }
}