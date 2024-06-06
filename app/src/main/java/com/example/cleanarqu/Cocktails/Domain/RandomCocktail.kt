package com.example.cleanarqu.Cocktails.Domain

import com.example.cleanarqu.Cocktails.Model.Repository
import com.example.cleanarqu.Cocktails.ui.State.CocktailState
import javax.inject.Inject

class RandomCocktail@Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): CocktailState {
        return repository.vCocktailRandom()
    }
}