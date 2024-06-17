package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel:LocationViewModel=viewModel()
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(viewModel = LocationViewModel())
                }
            }
        }
    }
}

//composable function for app
@Composable
fun MyApp(viewModel: LocationViewModel){
    val context= LocalContext.current
    val locationUtils=LocationUtils(context)
    LocationDisplay(locationUtils = locationUtils, viewModel = LocationViewModel(), context =context )
}
// front display screen ui/ux
@Composable
fun LocationDisplay(locationUtils: LocationUtils,
                    viewModel: LocationViewModel,
                    context:Context){
    val location =viewModel.location.value

    val requestPermissionLauncher= rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestMultiplePermissions() ,
        onResult = {permissions->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION]==true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION]==true)

                //i have permsssion
                locationUtils.requestLocationUpdates(viewModel)
                else{
                  val rationalRequired=ActivityCompat.shouldShowRequestPermissionRationale(
                      context as MainActivity ,Manifest.permission.ACCESS_FINE_LOCATION)
                          || ActivityCompat.shouldShowRequestPermissionRationale(
                              context as MainActivity ,Manifest.permission.ACCESS_COARSE_LOCATION)
                if (rationalRequired){
                    Toast.makeText(context,"location permission is required for this feature to work",
                        Toast.LENGTH_LONG).show()
                }else{Toast.makeText(context,"location permission is required for this feature to work. please turn it on from android settings",
                    Toast.LENGTH_LONG).show()}

                }
        })

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){


        if(location!=null){
            Text(text = "Address:${location.latitude} ${location.longitude}")
        }
        else{
            Text(text = "Location Not Available")
        }

        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)){
                locationUtils.requestLocationUpdates(viewModel)


            }else {
                //permission has already been granted
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION

                    )
                )
            }
            }){ Text(text = "Get location")
            }
        }
    }
