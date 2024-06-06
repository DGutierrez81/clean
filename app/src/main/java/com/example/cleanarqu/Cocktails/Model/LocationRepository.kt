package com.example.cleanarqu.Cocktails.Model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager


import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.media3.ui.PlayerNotificationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.google.type.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
/*
class LocationRepository@Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient
): LocationService {
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun requestLocationUpdates(): Location? {
        return suspendCoroutine { cont ->
            val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            val isUserLocationPermissionsGranted = true
            val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)

            if(!isGPSEnabled || !isUserLocationPermissionsGranted){
                cont.resume(null)
                return@suspendCoroutine
            }

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val location = task.result
                        cont.resume(location)
                    } else {
                        cont.resume(null)
                    }
                }
            } else {
                cont.resume(null)
            }
        }
    }

    override fun requestCurrentLocation(): Flow<LatLng?> {
        TODO("Not yet implemented")
    }
}

 */



    fun Context.hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

