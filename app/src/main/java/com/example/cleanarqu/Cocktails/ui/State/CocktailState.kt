package com.example.cleanarqu.Cocktails.ui.State

import com.example.cleanarqu.Cocktails.Model.Response.DrinksInfo

data class CocktailState(
    val drinks: List<drinkState>? = emptyList()
)
