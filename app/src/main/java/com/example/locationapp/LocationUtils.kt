package com.example.locationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationUtils(val context: Context) {

    private val _fusedLocationClient:FusedLocationProviderClient=
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermissions", "MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel){
        val loacationCallBack=object:LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation?.let {
                    val location=Locationdata(latitude = it.latitude, longitude = it.latitude)
                    viewModel.updateLocation(location)
                }
            }
        }

        val locationRequest=LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).build()

        _fusedLocationClient.requestLocationUpdates(locationRequest,loacationCallBack, Looper.getMainLooper())
    }


    //this function tells us if user has provided us with location permission or not
    fun hasLocationPermission(context:Context):Boolean{

        return ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED


    }
}