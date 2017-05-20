package com.rviannaoliveira.vroutetrack.route

import com.rviannaoliveira.vroutetrack.model.RegisterTrack

/**
 * Criado por rodrigo on 20/05/17.
 */

interface RouteView{
    fun loadGoogleApi()
    fun updateCurrentLocation()
    fun loadView(registers: List<RegisterTrack>)
    fun refresh(registers: List<RegisterTrack>)


}
