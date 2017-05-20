package com.rviannaoliveira.vroutetrack.data

import com.rviannaoliveira.vroutetrack.model.RegisterTrack
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers



/**
 * Criado por rodrigo on 20/05/17.
 */
object DataManager {
    private val registerDao = RoomDatabase.getDefaultInstance().registerDao()

    fun insert(register : RegisterTrack){
        Observable.just(register)
                .subscribeOn(Schedulers.newThread())
                .subscribe({ register ->
                    registerDao.insert(register)
                },{ e ->
                    error(e)
                })
    }

    fun getAllRegisters(): Flowable<List<RegisterTrack>> {
        return registerDao.getAllRegisters()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ list -> Flowable.fromArray(list)})
    }

}