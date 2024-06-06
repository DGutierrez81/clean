package com.example.cleanarqu.Cocktails.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController

@androidx.annotation.OptIn(UnstableApi::class) 
@Composable
fun Video(navController: NavController, viewmodel: Viewmodel) {

    //val url = viewmodel.resultUri
    val url = viewmodel.uriVideo
    //val url = "https://firebasestorage.googleapis.com/v0/b/pruebacocktail-5666d.appspot.com/o/download%2F20240425_041351image94684085564313445jpg?alt=media&token=f69e846d-f648-4da5-8cf9-10ee5dd677be"
    //val url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val mediaPlayer = ExoPlayer.Builder(LocalContext.current).build()
    val media = url?.let { MediaItem.fromUri(it) }
    


    LaunchedEffect(media) {
        if(media != null){
            mediaPlayer.setMediaItem(media)
            mediaPlayer.pause()
        }
        // Si falla ocultar la siguiente línea.
        mediaPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

        mediaPlayer.prepare()
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = mediaPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    

}