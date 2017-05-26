package com.rviannaoliveira.vroutetrack.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.rviannaoliveira.vroutetrack.data.DataManager
import com.rviannaoliveira.vroutetrack.model.RegisterTrack
import com.rviannaoliveira.vroutetrack.util.RouteUtil


/**
 * Criado por rodrigo on 17/05/17.
 */
class RouteTrackService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private lateinit var googleApi: GoogleApiClient
    private var lastLocation: Location? = null
    private lateinit var locationRequest: LocationRequest
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5 * 1000 * 60
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    private val DISPLACEMENT_DISTANCE = 100f

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
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApi, this)
            googleApi.disconnect()
        }
        return super.stopService(name)
    }


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
        if (googleApi.isConnected) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApi, locationRequest, this)
        }
    }


    override fun onConnectionSuspended(p0: Int) {
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.smallestDisplacement = DISPLACEMENT_DISTANCE
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    override fun onLocationChanged(location: Location) {
        if (lastLocation == null || location.distanceTo(lastLocation) > 50 ) {
            insertRegister(location)
            lastLocation = location
        }
    }

    private fun insertRegister(location: Location) {
        val register = RegisterTrack()
        register.latitude = location.latitude
        register.longitude = location.longitude
        register.timeStamp = System.currentTimeMillis()
        register.address = RouteUtil.getAddressLabel(this, LatLng(location.latitude, location.longitude))
        DataManager.insert(register)
    }
}

