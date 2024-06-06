package com.example.cleanarqu.Cocktails.Model

data class MainScreenState(
    val textToBeTranslate: String = "",
    val translatedText: String = "",
    val isButtonEnabled: Boolean = true,
)
