package com.rviannaoliveira.vroutetrack.route

import com.rviannaoliveira.vroutetrack.data.DataManager

/**
 * Criado por rodrigo on 20/05/17.
 */
class RoutePresenterImpl(private val view : RouteView) : RoutePresenter {
    override fun refreshData(){
         DataManager.getAllRegisters().subscribe({
             list -> view.refresh(list)
         },{e ->
             error(e)
         })
    }

    override fun init() {
        view.loadGoogleApi()
        DataManager.getAllRegisters().subscribe({ list ->
            view.loadView(list)
        }, { e ->
            error(e)
        })
    }
}