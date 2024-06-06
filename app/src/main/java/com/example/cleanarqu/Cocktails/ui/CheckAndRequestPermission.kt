package com.example.cleanarqu.Cocktails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckAndRequestPermission() {
    /*
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Solicitar permiso en un efecto lanzado cuando la composición se inicia
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    when {
        locationPermissionState.status.isGranted -> {
            // Permiso concedido, puedes acceder a la ubicación aquí
            MyGoogleMaps()
        }
        locationPermissionState.status.shouldShowRationale -> {
            // Explica por qué necesitas el permiso
            Text("Necesitamos tu permiso para acceder a la ubicación para mostrarla en el mapa.")
        }
        !locationPermissionState.status.isGranted -> {
            // El permiso fue denegado o aún no ha sido solicitado
            Text("Permiso de ubicación no concedido. No podemos mostrar la ubicación.")
        }
    }

     */
}

