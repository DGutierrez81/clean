@file:Suppress("DEPRECATION")

package com.example.cleanarqu.Cocktails.ui

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cleanarqu.Cocktails.Model.Routes
import com.example.cleanarqu.R


@Composable
fun Camera(navController: NavController, viewmodel: Viewmodel, context: ComponentActivity){


    var uri: Uri? by rememberSaveable{ mutableStateOf(null) }
    //val uri: Uri? by viewmodel.uri.collectAsState(null)
    var showDialog: Boolean by remember{ mutableStateOf(false)}
    var showDialog2: Boolean by remember{ mutableStateOf(false)}
    //var resultUri: Uri? by remember { mutableStateOf(null)}
    var resultUri = viewmodel.resultUri
    val loading: Boolean by viewmodel.isLoading.collectAsState()
    val namePhoto = viewmodel.namePhoto
    val focusRequester = viewmodel.focusRequest
    val focusManager = LocalFocusManager.current
    
    val metada = viewmodel.metada

    val intentCameraLaucher = viewmodel.intentCameraLaucher(uri, focusManager)

    val intentCameraLaucherVideo = viewmodel.intentCameraLaucherVideo(result = uri, focusManager = focusManager)

    val intentGalleryLancher = viewmodel.intentGalleryLaucher()






/*
    var intentCameraLaucher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
        if(it && uri?.path?.isNotEmpty() == true){
            //viewmodel.uploadBasicImage(uri!!)
            viewmodel.uploadAndGetImage(uri!!){ newUri -> viewmodel.changeResultUri(newUri)}
        }
    }

 */

    /*
    val intentGalleryLancher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        if(it?.path?.isNotEmpty() == true){
            //viewmodel.uploadBasicImage(it)
            viewmodel.uploadAndGetImage(it){ newUri -> resultUri = newUri}
        }
    }

     */

    if(showDialog2){
        Dialog(onDismissRequest = { showDialog2 = false }) {
            Card(shape = RoundedCornerShape(12)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    OutlinedButton(onClick = {
                        uri = viewmodel.generateUri(context, namePhoto, "jpg")
                        intentCameraLaucher.launch(uri)
                        showDialog2 = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                    ) {
                        Text(text = "Foto", color = colorResource(id = R.color.lila))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = {
                        uri = viewmodel.generateUri(context, namePhoto, "mp4")
                        intentCameraLaucherVideo.launch(uri)
                        showDialog2 = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        border = BorderStroke(2.dp, colorResource(id = R.color.lila))
                    ) {
                        Text(text = "Video", color = colorResource(id = R.color.lila))
                    }
                }
            }

        }
    }

    if(showDialog){
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(shape = RoundedCornerShape(12)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    OutlinedButton(onClick = {
                        showDialog2 = true
                        /*
                        //uri = generateUri(context)
                        uri = viewmodel.generateUri(context, namePhoto, "jpg")
                        //val generate = generateUri(context)
                        //viewmodel.changeUri(context)
                        intentCameraLaucher.launch(uri)

                         */
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
                        intentGalleryLancher.launch("*/*")
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
        Card(elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(colorResource(id = R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 36.dp)
                .shadow(
                    elevation = 25.dp,
                    ambientColor = colorResource(id = R.color.verde),
                    spotColor = colorResource(id = R.color.lila),
                    shape = RoundedCornerShape(20.dp)
                )){
            if(resultUri != null){
                AsyncImage(model = resultUri, contentDescription = "image selected by user", contentScale = ContentScale.Crop)
                viewmodel.changeUriVideo(resultUri.toString())
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
                    viewmodel.choose(metada)
                },
                    containerColor = colorResource(R.color.verde),
                    contentColor = colorResource(id = R.color.negrete)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "icono cámara" )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Mytext(namePhoto = namePhoto, viewmodel = viewmodel, focusRequester)
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = { navController.navigate("listPhotos") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
                .align(Alignment.CenterHorizontally),
            border = BorderStroke(2.dp, colorResource(id = R.color.verde)),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Lista de fotos", color = colorResource(id = R.color.lila))
        }
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Button(onClick = { navController.navigate("video") }) {
                Text(text = "Video")
            }
            Button(onClick = { navController.navigate("createNewCocktail") }) {
                Text(text = "Crear Cocktail")
            }

        }
        
        Text(text = metada)
        Spacer(modifier = Modifier.weight(2f))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mytext(namePhoto: String, viewmodel: Viewmodel, focusRequester: FocusRequester){
    TextField(value = namePhoto, onValueChange = {viewmodel.changeNamePhoto(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
            .border(2.dp, color = colorResource(id = R.color.verde), RoundedCornerShape(22))
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
    )
        
}

/*
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