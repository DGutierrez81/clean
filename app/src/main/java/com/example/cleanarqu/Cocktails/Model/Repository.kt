package com.example.cleanarqu.Cocktails.Model

import com.example.cleanarqu.Cocktails.Model.Response.Cocktail
import com.example.cleanarqu.Cocktails.Model.Response.DrinksInfo
import com.example.cleanarqu.Cocktails.ui.State.CocktailState
import com.example.cleanarqu.Cocktails.ui.State.drinkState
import javax.inject.Inject

class Repository @Inject constructor(private val api: CocktailsView){

    suspend fun vCocktail(name: String): CocktailState{
        //return api.vCocktail()
        val response = api.getCocktail(name)
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else {
            CocktailState()
        }
    }

    suspend fun vCocktailRandom(): CocktailState{
        //return api.vCocktail()
        val response = api.getRandom()
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else {
            CocktailState()
        }
    }



    private fun Cocktail.getDrink(): CocktailState{
        return CocktailState(
            drinks = this.drinks?.map { it.getDrinkState() }
        )
    }

    private fun DrinksInfo.getDrinkState(): drinkState{
        return drinkState(
            idDrink = this.idDrink ?: "vacio",
            strDrink = this.strDrink ?: "vacio",
            strInstructions = this.strInstructions ?: "vacio",
            strDrinkThumb = this.strDrinkThumb ?: "vacio",
            strIngredient = mutableListOf(strIngredient1 ?: "", strIngredient2 ?: "", strIngredient3 ?: "", strIngredient4 ?: "", strIngredient5 ?: "", strIngredient6 ?: "", strIngredient7 ?: "",
                strIngredient8 ?: "", strIngredient9 ?: "", strIngredient10 ?: "", strIngredient11 ?: "", strIngredient12 ?: "",
                strIngredient13 ?: "", strIngredient14 ?: "", strIngredient15 ?: "",)
            /*
            strDrinkThumb = this.strDrinkThumb ?: "vacio",
            strIngredient1 = this.strIngredient1 ?: "vacio",
            strIngredient2 = this.strIngredient2 ?: "vacio",
            strIngredient3 = this.strIngredient3 ?: "vacio",
            strIngredient4 = this.strIngredient4 ?: "vacio",
            strIngredient5 = this.strIngredient5 ?: "vacio",
            strIngredient6 = this.strIngredient6 ?: "vacio",
            strIngredient7 = this.strIngredient7 ?: "vacio",
            strIngredient8 = this.strIngredient8 ?: "vacio",
            strIngredient9 = this.strIngredient9 ?: "vacio",
            strIngredient10 = this.strIngredient10 ?: "vacio",
            strIngredient11= this.strIngredient11 ?: "vacio",
            strIngredient12 = this.strIngredient12 ?: "vacio",
            strIngredient13 = this.strIngredient13 ?: "vacio",
            strIngredient14 = this.strIngredient14 ?: "vacio",
            strIngredient15 = this.strIngredient15 ?: "vacio",

             */
        )
    }
}