package com.example.cleanarqu.Cocktails.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.R
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ViewConktailUser(navController: NavController, viewModel: Viewmodel) {
    val cocktailData by viewModel.cocktailData.collectAsState()
    val show = viewModel.show.value
    val id = viewModel.idDoc
    val context = LocalContext.current
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var average by remember {
        mutableStateOf(0.0)
    }
    var averageRating = viewModel.averageRating

    LaunchedEffect(Unit){
        viewModel.fetchCoctail()
    }

    if(showDialog){
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                Text(text = "Seleccione algún cocktail")
                OutlinedButton(onClick = { showDialog = false }) {
                    Text(text = "Cerrar diálogo")
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { if (id == "") showDialog = true else viewModel.mostrar() }) {
                Text(text = "Pulsa aquí")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cocktailData) { index, item ->
                if (index % 2 == 0) {
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < cocktailData.size
                    val nextItem = if (hasNextItem) cocktailData[nextIndex] else null

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = index.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Row(
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item.strDrinkThumb?.let { url ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        modifier = Modifier.clickable {
                                            viewModel.lightRow(show)

                                            navController.navigate("screen4/${item.idDocument}")
                                        }
                                    ){
                                        AsyncImage(
                                            model = url,
                                            contentDescription = "image selected by user",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .width(120.dp)
                                                .height(120.dp)
                                                .clip(RoundedCornerShape(100.dp))
                                        )
                                        Text(text = item.strDrink?:"", modifier = Modifier
                                            .height(75.dp)
                                            .width(120.dp)
                                            .padding(16.dp)
                                            .align(Alignment.CenterHorizontally),
                                            textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(1.dp))
                                        average = viewModel.calculateAverage(item.votes ?: 0, item.puntuacion ?: 0.0)

                                        Box(modifier = Modifier.width(120.dp), contentAlignment = Alignment.Center) {
                                            RatingBar2(rating = average)
                                        }
                                    }
                                }
                            }

                            nextItem?.strDrinkThumb?.let { url ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .clickable {
                                                viewModel.lightRow(show)
                                                navController.navigate("screen4/${nextItem.idDocument}")
                                            }
                                            .border(
                                                width = 2.dp,
                                                color = viewModel.changeColorRow(item.idDrink)
                                            )
                                    ) {
                                        AsyncImage(
                                            model = url,
                                            contentDescription = "image selected by user",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .width(120.dp)
                                                .height(120.dp)
                                                .clip(RoundedCornerShape(100.dp))
                                        )
                                        Text(text = nextItem.strDrink?:"", modifier = Modifier
                                            .height(75.dp)
                                            .width(120.dp)
                                            .padding(16.dp)
                                            .align(Alignment.CenterHorizontally),
                                            textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(1.dp))
                                        average = viewModel.calculateAverage(nextItem.votes ?: 0, nextItem.puntuacion ?: 0.0)

                                        Box(modifier = Modifier.width(120.dp), contentAlignment = Alignment.Center) {
                                            RatingBar2(rating = average)
                                        }

                                    }
                                }
                            }
                        }


                        /*
                        Spacer(modifier = Modifier.weight(1f))

                        average = viewModel.calculateAverage(item.votes ?: 0, item.puntuacion ?: 0.0)

                        Box(modifier = Modifier.width(200.dp)) {
                            RatingBar2(rating = average)
                        }

                        if (viewModel.showAlert) {
                            navController.navigate("screen4/${id}")
                            viewModel.lightRow(show)
                        }

                         */
                    }
                }
            }
        }
    }
}



        /*
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cocktailData) { index, item -> // Utilizamos itemsIndexed en lugar de items


                //val backgroundColor = if (index % 2 == 0) Color(0xFFF9EBE0) else Color.White // Alternar entre dos colores para las filas impares y pares

                Column {
                    Text(
                        text = item.strDrink ?: "vacio",
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
                                    idDrink = item.idDrink ?: "vacio",
                                    strDrink = item.strDrink ?: "vacio",
                                    strInstructions = item.strInstructions ?: "vacio",
                                    strDrinkThumb = item.strDrinkThumb ?: "vacio",
                                    strList = item.strList ?: mutableListOf(),
                                    strMedia = item.strmedia,
                                )
                                viewModel.changeSelectedRow(item.idDrink)
                                viewModel.changeIdDocument(item.idDocument ?: "")
                            }
                            .border(
                                width = 2.dp,
                                color = viewModel.changeColorRow(item.idDrink)
                            )
                    ) {
                        Box(
                            modifier = Modifier.width(100.dp)
                        ) {
                            AsyncImage(model = item.strDrinkThumb, contentDescription = "image selected by user", contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)))
                            /*
                            Image(
                                painter = rememberImagePainter(item.strDrinkThumb),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                            )

                             */
                        }







                        /*
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(viewModel.calculateBackgroundColor(index)) // Establecer el color de fondo del contenedor
                        ) {
                            Text(
                                //"Ingredients:\n${item.strIngredient1}, ${item.strIngredient2}, ${item.strIngredient3},${item.strIngredient4}\n${item.strIngredient11}"
                                text = "Ingredients:\n" + item.strList?.joinToString(),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }



                         */

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    average = viewModel.calculateAverage(item.votes ?: 0, item.puntuacion?: 0.0)

                        // Mostrar el rango real
                        //Text(text = "Rango real: $averageRating")
                        Box(modifier = Modifier.width(200.dp)){
                            RatingBar2(rating = average)
                        }

                    if (viewModel.showAlert) {
                        navController.navigate("screen4/${id}")
                        viewModel.lightRow(show)
                    }
                }
            }
        }
    }


         */





@Composable
fun RatingBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    onRatingChanged: (Double) -> Unit
) {
    Row(modifier = modifier) {
        repeat(stars) { index ->
            val icon = if (index < rating) {
                painterResource(R.drawable.star)
            } else {
                painterResource(R.drawable.star_border)
            }
            Icon(
                painter = icon,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.clickable {
                    val newRating = index + 1.toDouble() // índice comienza desde 0
                    onRatingChanged(newRating)
                }
            )
        }
    }
}


@Composable
fun RatingBar2(
    modifier: Modifier = Modifier.fillMaxWidth(),
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }
        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.star_half),
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                painter = painterResource(R.drawable.star_border),
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}



/*
@Composable
fun RatingBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    onRatingChanged: (Double) -> Unit
) {
    val filledStars = floor(rating).toInt()
    val hasHalfStar = rating.rem(1) >= 0.5
    Row(modifier = modifier) {
        repeat(stars) { index ->
            val icon = when {
                index < filledStars -> painterResource(R.drawable.star)
                index == filledStars && hasHalfStar -> painterResource(R.drawable.star_half)
                else -> painterResource(R.drawable.star_border)
            }
            Icon(
                painter = icon,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.clickable {
                    val newRating = if (index == filledStars && hasHalfStar) {
                        index + 0.5
                    } else {
                        index.toDouble() + 1
                    }
                    onRatingChanged(newRating)
                }
            )
        }
    }
}
 */






