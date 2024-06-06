package com.example.cleanarqu.Cocktails.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.R

@Composable
fun Screen4(navController: NavController, viewModel: Viewmodel, context: ComponentActivity, Idoc: String){
    val state = viewModel.state.value
    val actionTranslate = viewModel.actionTranslate
    var currentRating = viewModel.currentRating
    val votes = viewModel.votes
    val puntuacion = viewModel.puntuacion
    var showVotes by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        viewModel.getCoctailById(Idoc)
    }
    val drink = viewModel.drink

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Card(
            border = BorderStroke(2.dp, Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)

        ) {
            AsyncImage(
                model = drink.strDrinkThumb,
                contentDescription = "image selected by user",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            /*
            Image(
                painter = rememberAsyncImagePainter(viewModel.drink.strDrinkThumb),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

             */

            LazyColumn(
                modifier = Modifier.background(Color.White)
            ) {
                item() {
                    drink.strDrink?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    if (actionTranslate) {
                        drink.strInstructions?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        viewModel.onTextToBeTranslatedChange(
                            drink.strInstructions ?: "Empty text"
                        )
                        viewModel.onTranslateButtonClick(
                            text = state.textToBeTranslate,
                            context = context
                        )
                    } else {
                        state.translatedText.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Text(
                        text = "Ingredients:\n${drink.strList?.joinToString()}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(text = drink.puntuacion.toString())
                    Text(text = drink.votes.toString())
                    Text(text = Idoc)
                    Text(text = drink.imageName.toString())

                }



                //viewModel.changeValuePoint(drink.puntuacion)
                //viewModel.changeValue(drink.votes)

            }
        }

        Column {
            Row {
                Button(onClick = {
                    viewModel.changeUriVideo(drink.strmedia ?: "")
                    navController.navigate("video")
                }) {
                    Text(text = "Ver video")
                }

                Button(onClick = {
                    navController.navigate("screenHome")
                    viewModel.mostrar()
                }) {
                    Text(text = "volver")
                }

                Button(
                    onClick = {
                        viewModel.changeActionTranslate(!actionTranslate)
                    },
                    enabled = state.isButtonEnabled
                ) {
                    Text(text = "Translate")
                }

            }

            Row {
                Button(onClick = { showVotes = true}) {
                    Text(text = "Votar")
                }
                Button(onClick = {
                    viewModel.deleteImage(drink.imageName.toString(), context)
                    //viewModel.deleteImage(drink.videoName?:"", context)
                    viewModel.deleteCocktail(Idoc){navController.navigate("viewCocktailUser")} }) {
                    Text(text = "Borrar")
                }
            }

        }

        if (showVotes) {
            Dialog(onDismissRequest = { showVotes = false }) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                    contentAlignment = Alignment.Center) {
                    Column {
                        RatingBar(
                            rating = currentRating,
                            onRatingChanged = { newRating ->
                                viewModel.changeCurrentRating(newRating)
                                viewModel.updateListVotes(newRating)
                            }
                        )

                        // Calcular la media de todos los votos
                        viewModel.calculateAverageRating()


                        Column {

                            OutlinedButton(onClick = {
                                viewModel.changeValueVotes(currentRating, "puntuacion")
                                viewModel.changeValueVotes( 1.0, "votes")
                                viewModel.updateStars(Idoc){navController.popBackStack()}
                                viewModel.cleanVotes()
                                showVotes = false
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                                border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                            ) {
                                Text(text = "Votar", color = colorResource(id = R.color.lila))
                            }
                        }
                    }
                }
            }

        }
    }

}

/*
val state = viewmodel.state.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        TextField(value = state.textToBeTranslate,
            onValueChange = {
                viewmodel.onTextToBeTranslatedChange(it)
            }
        )
        Spacer(modifier = Modifier.height(7.dp))
        Button(onClick = {
            viewmodel.onTranslateButtonClick(
                text = state.textToBeTranslate,
                context = context
            )
        },
            enabled = state.isButtonEnabled) {
            Text(text = "Translate")
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(text = state.translatedText)

    }
 */


/*
Row {
            Button(onClick = {
                viewModel.changeUriVideo(viewModel.drink.strmedia ?: "Te joe")
                navController.navigate(Routes.Video.Route)
            }) {
                Text(text = "Ver video")
            }

            Button(onClick = { navController.navigate(Routes.ScreenHome.Route)
                viewModel.mostrar()}) {
                Text(text = "volver")
            }
        }
 */