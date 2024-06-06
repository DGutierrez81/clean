package com.example.cleanarqu.Cocktails.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.R




@Composable
fun listPhotos(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){
    val uiState by viewmodel.uiState.collectAsState()
    var selectedImageName by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        viewmodel.getAllImages()
    }


    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)){
        items(uiState.images){ image ->
            Column {
                Card(
                    modifier = Modifier.border(BorderStroke(3.dp, color = colorResource(id = R.color.lila)))
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "images",
                        modifier = Modifier.clickable { selectedImageName = image.toUri().lastPathSegment.toString()
                            //viewmodel.changeResultUri(image.toUri())
                            //viewmodel.changeUriFoto(image.toUri().toString())
                        //navController.navigate("createNewCocktail")
                        }
                    )
                }
                Text(text = image.toUri().toString(), modifier = Modifier.clickable {
                    //viewmodel.changeResultUriVideo(image.toUri())
                    //viewmodel.changeUriVideo(image.toUri().toString())
                    navController.navigate(Routes.CreateNewCocktail.Route)
                })
                Text(text = image.toUri().lastPathSegment.toString(), color = Color.Red)
                Text(text = selectedImageName.toString(), color = Color.Green)

                Button(onClick = { navController.navigate("video") }) {
                    Text(text = "Video")
                }
            }
        }
    }

    // Borrar la imagen seleccionada
    selectedImageName?.let { imageName ->
        AlertDialog(
            onDismissRequest = { selectedImageName = null },
            title = { Text(text = "¿Deseas eliminar esta imagen?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Llama a la función de tu ViewModel para eliminar la imagen
                        viewmodel.deleteImage(imageName, context)
                        navController.navigate(Routes.Camera.Route)
                        //selectedImageName = null
                    }
                ) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { selectedImageName = null }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}