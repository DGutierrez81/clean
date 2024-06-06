package com.example.cleanarqu

import android.content.Context
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.Cocktails.ui.BlankView
import com.example.cleanarqu.Cocktails.ui.Camera
import com.example.cleanarqu.Cocktails.ui.Cocktails
import com.example.cleanarqu.Cocktails.ui.CreateNewCocktail
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
import com.example.cleanarqu.ui.theme.CleanArquTheme
import com.google.maps.android.compose.GoogleMap
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import android.Manifest
import android.annotation.SuppressLint

import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute
import android.util.Log

import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.cleanarqu.Cocktails.Navigation.NavaManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: Viewmodel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArquTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //Camera()

                    //MyGoogleMaps()

                    NavaManager(viewmodel = viewModel, context = this@MainActivity)

                }
            }
        }
    }
}



/*
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: Viewmodel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            CleanArquTheme {
                var location by remember { mutableStateOf<Location?>(null) }

                val context = LocalContext.current

                val locationPermissionState = rememberPermissionState(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                LaunchedEffect(Unit) {
                    locationPermissionState.launchPermissionRequest()
                }

                if (locationPermissionState.hasPermission) {
                    getLastLocation { loc ->
                        location = loc
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            location?.let {
                                val gmmIntentUri = Uri.parse("geo:${it.latitude},${it.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                context.startActivity(mapIntent)
                            } ?: run {
                                Toast.makeText(context, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(text = "Abrir Google Maps")
                    }

                    Button(
                        onClick = {
                            location?.let {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Mi ubicación actual: ${it.latitude}, ${it.longitude}")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            } ?: run {
                                Toast.makeText(context, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(text = "Compartir ubicación")
                    }
                }
            }
        }
    }

    private fun getLastLocation(onLocationResult: (Location?) -> Unit) {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    onLocationResult(location)
                }
        } else {
            onLocationResult(null)
        }
    }
}
 */


/*
@Composable
fun MyGoogleMaps(){
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}


 */
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


/*
@Composable
fun Camera(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){


    var uri: Uri? by remember{ mutableStateOf(null) }
    var showDialog: Boolean by remember{ mutableStateOf(false)}
    var resultUri: Uri? by remember { mutableStateOf(null)}
    val loading: Boolean by viewmodel.isLoading.collectAsState()

    var intentCameraLaucher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
        if(it && uri?.path?.isNotEmpty() == true){
            //viewmodel.uploadBasicImage(uri!!)
            viewmodel.uploadAndGetImage(uri!!){ newUri -> resultUri = newUri}
        }
    }
    val intentGalleryLancher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        if(it?.path?.isNotEmpty() == true){
            //viewmodel.uploadBasicImage(it)
            viewmodel.uploadAndGetImage(it){ newUri -> resultUri = newUri}
        }
    }

    if(showDialog){
        Dialog(onDismissRequest = { showDialog = false }) {

            Card(shape = RoundedCornerShape(12)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    OutlinedButton(onClick = {
                        uri = generateUri(context)
                        intentCameraLaucher.launch(uri)
                        showDialog = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                    ) {
                        Text(text = "Camera", color = colorResource(id = R.color.lila))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = {
                        intentGalleryLancher.launch("image/*")
                        showDialog = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                    ) {
                        Text(text = "Galery", color = colorResource(id = R.color.lila))
                    }
                }
            }

        }
    }

    Column(modifier = Modifier.fillMaxSize()){
        Spacer(modifier = Modifier.height(16.dp))
        Card(elevation = CardDefaults.cardElevation(12.dp),
            shape = RoundedCornerShape(12),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 36.dp)){
            if(resultUri != null){
                AsyncImage(model = resultUri, contentDescription = "image selected by user", contentScale = ContentScale.Crop)
            }

            if(loading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.size(50.dp), color = colorResource(
                        id = R.color.verde
                    ))
                }
            }
            if(!loading && resultUri == null){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Icon(
                        painterResource(id = R.drawable.ic_image),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = colorResource(id = R.color.verde)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Column {
                FloatingActionButton(onClick = {
                    showDialog = true
                },
                    containerColor = colorResource(R.color.verde),
                    contentColor = colorResource(id = R.color.negrete)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "icono cámara" )
                }
            }
        }

        Spacer(modifier = Modifier.weight(2f))
    }

}

fun generateUri(context: ComponentActivity): Uri {
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.example.cleanarqu.provider",
        createFile(context)
    )
}


fun createFile(context: ComponentActivity): File {
    val name: String = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())+"image"
    return File.createTempFile(name,"jpg", context.externalCacheDir)
}


 */




    /*
    @Composable
    fun Camera(){


        var uri: Uri? by remember{ mutableStateOf(null) }
        var showDialog: Boolean by remember{ mutableStateOf(false)}
        var intentCameraLaucher = rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()){
            if(it && uri?.path?.isNotEmpty() == true){
                viewModel.uploadBasicImage(uri!!)
            }
        }
        val intentGalleryLancher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
            if(it?.path?.isNotEmpty() == true){
                viewModel.uploadBasicImage(it)
            }
        }

        if(showDialog){
            Dialog(onDismissRequest = { showDialog = false }) {

                Card(shape = RoundedCornerShape(12)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        OutlinedButton(onClick = {
                            uri = generateUri()
                            intentCameraLaucher.launch(uri)
                            showDialog = false
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterHorizontally),
                            border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                        ) {
                            Text(text = "Camera", color = colorResource(id = R.color.lila))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(onClick = {
                            intentGalleryLancher.launch("image/*")
                            showDialog = false
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterHorizontally),
                            border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                        ) {
                            Text(text = "Galery", color = colorResource(id = R.color.lila))
                        }
                    }
                }

            }
        }


        Box(
            contentAlignment = Alignment.Center
        ){
            Column {
                FloatingActionButton(onClick = {
                    showDialog = true
                },
                    containerColor = colorResource(R.color.verde),
                    contentColor = colorResource(id = R.color.negrete)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "icono cámara" )
                }
            }
        }

    }

    fun generateUri(): Uri {
        return FileProvider.getUriForFile(
            Objects.requireNonNull(this),
            "com.example.cleanarqu.provider",
            createFile()
        )
    }


    fun createFile(): File {
        val name: String = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())+"image"
        return File.createTempFile(name,"jpg", externalCacheDir)
    }
}


     */
*/




 */
