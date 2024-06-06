package com.example.cleanarqu.Cocktails.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.cleanarqu.Cocktails.Model.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BlankView(navController: NavController){

    LaunchedEffect(key1 = true){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate("screenHome")
        }else{
            navController.navigate("screen")
        }
    }

}