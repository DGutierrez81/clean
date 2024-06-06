package com.example.cleanarqu.Cocktails.Model

data class CocktailUser(
    val emailUser: String? = "",
    val idDrink: String? = "",
    val strDrink: String? = "",
    val strInstructions: String? = "",
    val strDrinkThumb: String? = "",
    val strList: MutableList<String>? = mutableListOf(),
    val strmedia: String? = null,
    val imageName: String? = "",
    val videoName: String? = "",
    val votes: Int? = 0,
    val puntuacion: Double? = 0.0,
    val idDocument: String? = ""
)
