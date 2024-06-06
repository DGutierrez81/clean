package com.example.cleanarqu.Cocktails.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.collectAsState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.core.app.ActivityCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MyGoogleMaps(viewModel: Viewmodel, context: ComponentActivity) {
    val location by viewModel.location.collectAsState()
    val savedLocation by viewModel.savedLocation.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.requestLocation(context)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location ?: LatLng(0.0, 0.0), 10f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        location?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Mi Ubicación",
                snippet = "Estoy aquí"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                savedLocation?.let {
                    val gmmIntentUri = Uri.parse("geo:${it.latitude},${it.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                } ?: run {
                    Toast.makeText(context, "Ubicación guardada no disponible", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Abrir Google Maps")
        }

        Button(
            onClick = {
                location?.let {
                    viewModel.saveLocation(it)
                    Toast.makeText(context, "Ubicación guardada", Toast.LENGTH_SHORT).show()
                } ?: run {
                    Toast.makeText(context, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Compartir ubicación")
        }
    }
}




/*
@SuppressLint("MissingPermission")
@Composable
fun MyGoogleMaps() {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(key1 = true) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                currentLocation = LatLng(it.latitude, it.longitude)
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        currentLocation?.let {
            position = CameraPosition.fromLatLngZoom(it, 17f)
        }
    }

    if (currentLocation != null) {
        GoogleMap(
            modifier = Modifier.fillMaxWidth().height(500.dp),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = MapType.SATELLITE),
            uiSettings = MapUiSettings(zoomControlsEnabled = true),
        ) {
            Marker(state = MarkerState(currentLocation!!), title = "Tu Ubicación", snippet = "Aquí estás")
        }
    }
}

 */


/*
@Composable
fun MyGoogleMaps(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){

    LaunchedEffect(key1 = true){//Unit
        viewmodel.changeLocation(viewmodel.getUserLocation(context))
    }


    //val singapore = LatLng(36.5008762, -6.2684345)
    val lati: Location? by viewmodel.location.collectAsState()
    val a = LatLng(lati?.latitude?: 37.52672 , lati?.longitude?: -122.083922)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(a, 10f)
    }


    /*

    Column {
        Text(text = lati?.latitude.toString())
        Text(text = lati?.longitude.toString())
    }

     */





    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = a),
            title = "Cádiz",
            snippet = "Marker in Cádiz"
        )
    }




}

 */





/*
@Composable
fun MyGoogleMaps(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){
    val singapore = LatLng(36.5008762, -6.2684345)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Cádiz",
            snippet = "Marker in Cádiz"
        )
    }
}

@Composable
fun MyApp(activity: ComponentActivity) {
    var location by remember { mutableStateOf<String>("") }
    var isLoading by remember { mutableStateOf(false) }
    val locationProviderClient = remember(activity) { LocationServices.getFusedLocationProviderClient(activity) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val launcher = rememberLauncher(locationProviderClient, activity) { newLocation ->
            location = newLocation
        }
        Button(onClick = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
            Text("Compartir Ubicación")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            Text(text = "Obteniendo ubicación...")
        } else {
            Text(text = location)
        }
    }
}

fun getLocation(
    locationProviderClient: FusedLocationProviderClient,
    onLocationReceived: (String) -> Unit,
    activity: ComponentActivity
) {
    if (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
        return
    }

    locationProviderClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val lat = location.latitude
            val lng = location.longitude
            val locationString = "Latitud: $lat, Longitud: $lng"
            onLocationReceived(locationString)
        }
    }.addOnFailureListener { exception ->
        // Manejar cualquier excepción que pueda ocurrir al obtener la ubicación
    }
}

@Composable
fun rememberLauncher(
    locationProviderClient: FusedLocationProviderClient,
    context: ComponentActivity,
    onLocationReceived: (String) -> Unit
): ManagedActivityResultLauncher<String, Boolean> {
    val lifecycleOwner = LocalLifecycleOwner.current

    return rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            getLocation(locationProviderClient, onLocationReceived, context)
        } else {
            // Permiso denegado, maneja este caso según tus necesidades
        }
    }.apply {
        launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
const val REQUEST_LOCATION_PERMISSION = 101

 */

/*
@Composable
fun MyGoogleMaps(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){
    val singapore = LatLng(36.5008762, -6.2684345)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Cádiz",
            snippet = "Marker in Cádiz"
        )
    }
}


 */
fun chckForPermission(context: ComponentActivity): Boolean {
    return !(ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
        Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
            )
}

/*
fun isLocationPermissionGranted(context: ComponentActivity) = ContextCompat.checkSelfPermission(
    context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

fun requestLocationPermission(){
    //if(!::)
}


 */

/*
@Composable
fun MyGoogleMaps(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){
    val singapore = LatLng(36.5008762, -6.2684345)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Cádiz",
            snippet = "Marker in Cádiz"
        )
    }
}



GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapReady = { googleMap ->
            myLocation?.let { location ->
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                Marker(
                    state = MarkerState(position = location),
                    title = "Mi Ubicación",
                    snippet = "Ubicación Actual"
                )
            }
        }
    )
 */