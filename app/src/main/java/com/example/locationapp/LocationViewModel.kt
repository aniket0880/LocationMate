package com.example.locationapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel

class LocationViewModel :ViewModel(){
    private val _location= mutableStateOf<Locationdata?>(null)
    val location: State<Locationdata?> = _location

    fun updateLocation(newLocation:Locationdata){
        _location.value=newLocation
    }
}