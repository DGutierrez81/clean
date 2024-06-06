package com.example.cleanarqu.Cocktails.Model

sealed class Routes(val Route: String) {
    object screen: Routes("screen")
    object screen2: Routes("screen2")
    object cocktails: Routes("cocktails")
    object screen4: Routes("screen4")
    object BlankView: Routes("screen5")
    object ScreenHome: Routes("screen6")
    object ViewConktailUser: Routes("screen7")
    object Random: Routes("random")
    object Camera: Routes("camera")

    object ListPhotos: Routes("listPhotos")

    object Video: Routes("video")

    object CreateNewCocktail: Routes("createNewCocktail")

    object Translate: Routes("translate")

}