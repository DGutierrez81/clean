package com.example.cleanarqu.Cocktails.ui

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cleanarqu.Cocktails.Model.CocktailUser
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewCocktail(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){

    val namePhoto = viewmodel.nameCockFood
    var id = viewmodel.id
    var descripcion = viewmodel.descripcion
    var foto = viewmodel.uriFoto
    var video = viewmodel.uriVideo
    var ingrediente = viewmodel.ingrediente
    val focusRequester = viewmodel.focusRequest
    val imageName = viewmodel.imageName
    val videoName = viewmodel.videoName


    Column {
        Row {
            Text(text = "Id Cocktail")
            TextField(value = id, onValueChange = {viewmodel.changeId(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            )
        }
        Row {
            Text(text = "nombreCocktail")
            TextField(value = namePhoto, onValueChange = {viewmodel.changeNamecockFood(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            )
        }
        Row {
            Text(text = "Descripción")
            TextField(value = descripcion, onValueChange = {viewmodel.changeDescripcion(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            )
        }
        Row {
            Text(text = "Ingredientes")
            TextField(value = ingrediente, onValueChange = {viewmodel.changeIngrediente(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            )

        }

        Row {
            Text(text = "foto")
            TextField(value = foto, onValueChange = {viewmodel.changeUriFoto(it)},
                modifier = Modifier
                    .fillMaxWidth()

                    .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            )

        }
        Button(onClick = { navController.navigate("camera") }) {
            Text(text = "Buscar foto")
        }
        Row {
            Text(text = "video")
            TextField(value = video, onValueChange = {viewmodel.changeUriVideo(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
            )

        }
        Button(onClick = { navController.navigate("camera") }) {
            Text(text = "Buscar foto y video")
        }

        Button(onClick = {
            viewmodel.SaveCocktail(id, namePhoto, descripcion, foto, ingrediente.split(",", " ").toMutableList(), video, imageName, videoName)
            viewmodel.saveNewCocktail{ Toast.makeText(context, "Cocktail guardado correctamente", Toast.LENGTH_SHORT).show()
                navController.popBackStack() }
        }) {
            Text(text = "Enviar datos")
        }
    }
}