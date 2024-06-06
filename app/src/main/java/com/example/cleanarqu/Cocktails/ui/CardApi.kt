package com.example.cleanarqu.Cocktails.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun CardApi(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){
    val drink = viewmodel.drink
    val state = viewmodel.state.value
    val actionTranslate = viewmodel.actionTranslate




    Column {
        Card {
            AsyncImage(model = drink.strDrinkThumb,
                contentDescription = "foto cocktail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)))

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
                        viewmodel.onTextToBeTranslatedChange(
                            drink.strInstructions ?: "Empty text"
                        )
                        viewmodel.onTranslateButtonClick(
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
                }
            }
        }
        Row {

            Button(onClick = {
                navController.navigate("screenHome")
                viewmodel.mostrar()
            }) {
                Text(text = "volver")
            }

            Button(
                onClick = {
                    viewmodel.changeActionTranslate(!actionTranslate)
                },
                enabled = state.isButtonEnabled
            ) {
                Text(text = "Translate")
            }

        }
    }
}