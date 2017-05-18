package com.rviannaoliveira.vroutetrack

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices







/**
 * Criado por rodrigo on 17/05/17.
 */
class RouteTrackService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private lateinit var googleApi: GoogleApiClient
    private lateinit var lastLocation: Location
    private lateinit var locationRequest: LocationRequest
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 0
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    private val fusedLocationProviderApi = LocationServices.FusedLocationApi

    override fun onCreate() {
        super.onCreate()
        googleApi = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        createLocationRequest()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        googleApi.connect()
        return super.onStartCommand(intent, flags, startId)
    }


    override fun stopService(name: Intent?): Boolean {
        if (googleApi.isConnected) {
            stopLocationUpdates()
            googleApi.disconnect()
        }
        return super.stopService(name)
    }



    fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApi, this)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApi)
        Log.d(">>>>>> latitude ", lastLocation.latitude.toString())
        Log.d(">>>>>> longitude ", lastLocation.longitude.toString())
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun startLocationUpdates() {
        if (googleApi.isConnected) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApi, locationRequest, this)
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onLocationChanged(location: Location) {
        val suitableMeter = 20
        if (location.hasAccuracy() && location.accuracy <= suitableMeter) {
            Log.d(">>>>>> latitude ", location.latitude.toString())
            Log.d(">>>>>> longitude ", location.longitude.toString())
        }
    }
}

