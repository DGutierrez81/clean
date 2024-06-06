package com.example.cleanarqu.Cocktails.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


@Composable
fun Random(navController: NavController, viewModel: Viewmodel) {
    val nombre by viewModel.nombre.collectAsState()
    val show = viewModel.show.value
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { viewModel.mostrar() }) {
                Text(text = "Pulsa aqui")
            }
            Button(onClick = { viewModel.saveNewCocktail { Toast.makeText(context, "Nota guardada OK", Toast.LENGTH_SHORT).show()
                navController.popBackStack() } }) {
                Text(text = "Add cocktail")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(nombre) { index, item -> // Utilizamos itemsIndexed en lugar de items


                //val backgroundColor = if (index % 2 == 0) Color(0xFFF9EBE0) else Color.White // Alternar entre dos colores para las filas impares y pares

                Column {
                    Text(
                        text = item.strDrink,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Añadir un relleno alrededor del texto para que el color de fondo sea visible
                        textAlign = TextAlign.Center,
                        color = Color.Black // Color del texto
                    )
                    Row(
                        modifier = Modifier
                            .height(100.dp)
                            .clickable {
                                viewModel.lightRow(show)
                                viewModel.SaveCocktail(
                                    idDrink = item.idDrink,
                                    strDrink = item.strDrink,
                                    strInstructions = item.strInstructions ?: "vacio",
                                    strDrinkThumb = item.strDrinkThumb ?: "vacio",
                                    strList = item.strIngredient,
                                    strMedia = "",
                                    imageName = "",
                                    videoName = ""
                                    /*
                                    mutableListOf(
                                        item.strIngredient1 ?: "vacio", item.strIngredient2 ?: "vacio", item.strIngredient3 ?: "vacio", item.strIngredient4 ?: "vacio", item.strIngredient5 ?: "vacio", item.strIngredient6 ?: "vacio",
                                        item.strIngredient6 ?: "vacio", item.strIngredient7 ?: "vacio", item.strIngredient8 ?: "vacio", item.strIngredient9 ?: "vacio", item.strIngredient10 ?: "vacio", item.strIngredient12 ?: "vacio",
                                        item.strIngredient13 ?: "vacio", item.strIngredient14 ?: "vacio", item.strIngredient15 ?: "vacio",
                                    )

                                     */
                                )
                                viewModel.changeSelectedRow(item.idDrink)
                            }
                            .border(
                                width = 2.dp,
                                color = viewModel.changeColorRow(item.idDrink)
                            )
                    ) {
                        Box(
                            modifier = Modifier.width(100.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(item.strDrinkThumb),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(viewModel.calculateBackgroundColor(index)) // Establecer el color de fondo del contenedor
                        ) {
                            Text(
                                //"Ingredients:\n${item.strIngredient1}, ${item.strIngredient2}, ${item.strIngredient3},${item.strIngredient4}\n${item.strIngredient11}"
                                text = "Ingredients:\n" + item.strIngredient.joinToString(),
                                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                    if (viewModel.showAlert) {
                        navController.navigate("cardApi")
                        viewModel.lightRow(show)
                    }
                }
            }
        }
    }
}