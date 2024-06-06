package com.example.cleanarqu.Cocktails.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.Cocktails.ui.BlankView
import com.example.cleanarqu.Cocktails.ui.Camera
import com.example.cleanarqu.Cocktails.ui.CardApi
import com.example.cleanarqu.Cocktails.ui.CheckAndRequestPermission
//import com.example.cleanarqu.Cocktails.ui.CheckAndRequestPermission
import com.example.cleanarqu.Cocktails.ui.Cocktails
import com.example.cleanarqu.Cocktails.ui.CreateNewCocktail
import com.example.cleanarqu.Cocktails.ui.MyGoogleMaps
import com.example.cleanarqu.Cocktails.ui.Random
import com.example.cleanarqu.Cocktails.ui.Screen
import com.example.cleanarqu.Cocktails.ui.Screen2
import com.example.cleanarqu.Cocktails.ui.Screen4
import com.example.cleanarqu.Cocktails.ui.ScreenHome
import com.example.cleanarqu.Cocktails.ui.Translate
import com.example.cleanarqu.Cocktails.ui.Video
import com.example.cleanarqu.Cocktails.ui.ViewConktailUser
import com.example.cleanarqu.Cocktails.ui.Viewmodel
import com.example.cleanarqu.Cocktails.ui.listPhotos

@Composable
fun NavaManager(viewmodel: Viewmodel, context: ComponentActivity){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "blank"){
        composable("blank"){
            BlankView(navController = navController)
        }
        composable("screen"){
            Screen(navController = navController, loginVM = viewmodel)
        }
        composable("Screen2"){
            Screen2(navController = navController, loginVM = viewmodel)
        }
        composable("cocktails"){
            Cocktails(navController = navController, viewModel = viewmodel)
        }
        composable("screen4/{idDrink}", arguments = listOf(
            navArgument("idDrink"){type = NavType.StringType}
        )){
            val idDoc = it.arguments?.getString("idDrink") ?: ""
            Screen4(navController = navController, viewModel = viewmodel, context = context, idDoc)
        }
        composable("cardApi"){
            CardApi(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("Blankview"){
            BlankView(navController = navController)
        }
        composable("screenHome"){
            ScreenHome(navController = navController, viewModel = viewmodel)
        }
        composable("viewCocktailUser"){
            ViewConktailUser(navController = navController, viewModel = viewmodel)
        }
        composable("random"){
            Random(navController = navController, viewModel = viewmodel)
        }
        composable("camera"){
            Camera(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("listPhotos"){
            listPhotos(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("video"){
            Video(navController = navController, viewmodel = viewmodel)
        }
        composable("createNewCocktail"){
            CreateNewCocktail(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("translate"){
            Translate(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("myGoogleMaps"){
            MyGoogleMaps(viewmodel, context)
            //MyGoogleMaps()
           //CheckAndRequestPermission()
        }

    }
}

/*
val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.screen.Route){
                        composable(Routes.screen.Route){Screen(navController, viewModel)}
                        composable(Routes.screen2.Route){Screen2(navController, viewModel)}
                        composable(Routes.cocktails.Route){Cocktails(navController, viewModel)}
                        composable(Routes.screen4.Route){Screen4(navController, viewModel, context = this@MainActivity)}
                        composable(Routes.BlankView.Route){BlankView(navController)}
                        composable(Routes.ScreenHome.Route){ScreenHome(navController, viewModel)}
                        composable(Routes.ViewConktailUser.Route){ViewConktailUser(navController, viewModel)}
                        composable(Routes.Random.Route){Random(navController, viewModel)}
                        composable(Routes.Camera.Route){ Camera(navController, viewModel, this@MainActivity)}
                        composable(Routes.ListPhotos.Route){ listPhotos(navController,viewModel, this@MainActivity)}
                        composable(Routes.Video.Route){Video(navController,viewModel)}
                        composable(Routes.CreateNewCocktail.Route){ CreateNewCocktail(
                            navController = navController,
                            viewmodel = viewModel,
                            context =this@MainActivity
                        )}
                        composable(Routes.Translate.Route){ Translate(
                            navController = navController,
                            viewmodel = viewModel,
                            context = this@MainActivity
                        )}
                    }
 */