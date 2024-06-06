package com.example.cleanarqu.Cocktails.Domain

import com.example.cleanarqu.Cocktails.Model.Repository
import com.example.cleanarqu.Cocktails.Model.Response.Cocktail
import com.example.cleanarqu.Cocktails.ui.State.CocktailState
import com.example.cleanarqu.Cocktails.ui.State.drinkState
import javax.inject.Inject

class NameUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(nombre: String): CocktailState = repository.vCocktail(nombre)
}