package com.example.cleanarqu.Cocktails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.example.cleanarqu.Cocktails.Model.Routes

@Composable
fun ScreenHome(navController: NavController, viewModel: Viewmodel){

    LaunchedEffect(Unit){

    }


    Column {
        OutlinedTextField(
            value = viewModel.cocktailName,
            onValueChange = { viewModel.changeNameCocktail(it) },
            label = { androidx.compose.material3.Text(text = "Busqueda por nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(50.dp)
        )

        Button(onClick = {
            viewModel.getName(viewModel.cocktailName)
            navController.navigate("cocktails")
        }) {
            Text(text = "Margarita",
                modifier = Modifier.width(200.dp))

        }

        Button(onClick = {
            navController.navigate("viewCocktailUser")
        },
            modifier = Modifier.width(200.dp)) {
            Text(text = "UserCocktail")

        }

        Button(onClick = {
            viewModel.getRandom()
            navController.navigate("random")
        },
            modifier = Modifier.width(200.dp)) {
            Text(text = "Random")

        }

        Button(onClick = {
            navController.navigate("camera")
        },
            modifier = Modifier.width(200.dp)) {
            Text(text = "Camera")

        }

        Button(onClick = {
            navController.navigate("video")
        },
            modifier = Modifier.width(200.dp)) {
            Text(text = "ver video")

        }
        Button(onClick = {
            navController.navigate("createNewCocktail")
        },
            modifier = Modifier.width(200.dp)) {
            Text(text = "Crear Cocktail")

        }
        Button(onClick = {
            navController.navigate("myGoogleMaps")
        },
            modifier = Modifier.width(200.dp)) {
            Text(text = "Mapa")

        }
    }
}