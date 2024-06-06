package com.example.cleanarqu.Cocktails.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Translate(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){
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
}