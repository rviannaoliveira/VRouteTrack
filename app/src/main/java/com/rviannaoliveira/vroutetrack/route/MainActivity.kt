package com.rviannaoliveira.vroutetrack.route

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rviannaoliveira.vroutetrack.R
import com.rviannaoliveira.vroutetrack.model.RegisterTrack
import com.rviannaoliveira.vroutetrack.service.RouteTrackService


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RouteView{
    private var map: GoogleMap? = null
    private lateinit var recyclerView : RecyclerView
    private val PERMISSION = 123
    private var googleApiClient : GoogleApiClient? = null
    private lateinit var registerAdapter : RegisterAdapter
    private var registers = ArrayList<RegisterTrack>()
    private val routePresenter : RoutePresenter = RoutePresenterImpl(this)
    private lateinit var currentLocation : Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION)
        }
        routePresenter.init()
    }

    override fun onStart() {
        super.onStart()
        googleApiClient?.connect()
    }

    override fun onStop() {
        googleApiClient?.disconnect()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.refresh){
            routePresenter.refreshData()
        }else{
            startService(Intent(this, RouteTrackService::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        registers.forEach({ item ->
            map?.addMarker(MarkerOptions().position(LatLng(item.latitude!!, item.longitude!!)))
        })
        if(registers.isNotEmpty()){
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(registers.last().latitude!!, registers.last().longitude!!),11f))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            updateCurrentLocation()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        updateCurrentLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun updateCurrentLocation() {
        if(googleApiClient != null){
            this.currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
            map?.addMarker(MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
        }
    }

    override fun loadGoogleApi() {
        googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    override fun loadView(registers : List<RegisterTrack>) {
        this.registers.addAll(registers)
        recyclerView = findViewById(R.id.registers) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.registerAdapter = RegisterAdapter(this.registers,routePresenter)
        recyclerView.adapter = registerAdapter
        recyclerView.setHasFixedSize(true)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun refresh(registers: List<RegisterTrack>) {
        map?.clear()
        registerAdapter.refresh(registers)
        updateCurrentLocation()
    }


}
