package com.example.locationapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel :ViewModel(){
    private val _location= mutableStateOf<Locationdata?>(null)
    val location: State<Locationdata?> = _location
}