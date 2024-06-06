package com.example.cleanarqu.Cocktails.Model



import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream

import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StorageService@Inject constructor(private val storage: FirebaseStorage) {

    fun basicExample() {
        val reference = storage.reference.child("ejemplo/")
        reference.name //Cadiz.jpg
        reference.path// ejemplo/Cadiz.jpg
        reference.bucket //gs://pruebacocktail-5666d.appspot.com/ejemplo

        /*
        En el caso de que se tenga un directorio dentro de otro se podrá anidar y acceder a el
        Se puede anidar todos los directorios que queramos.

        val reference: StorageReference = storage.reference.child("ejemplo/").child("ejemplo2/")
         */
    }

    fun uploadBasicImage(uri: Uri) {
        val reference = storage.reference.child(uri.lastPathSegment.orEmpty())
        reference.putFile(uri)
    }

    suspend fun uploadAndDownloadImage(uri: Uri): Uri {
        return suspendCancellableCoroutine<Uri> { cancellableContinuation ->
            val reference: StorageReference =
                storage.reference.child("picture/${uri.lastPathSegment}")
            reference.putFile(uri).addOnSuccessListener {
                downloadImage(it, cancellableContinuation)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        }
    }

    /*
    suspend fun uploadVideoAndGetDownloadUrl(uri: Uri): Uri {
        return suspendCancellableCoroutine<Uri> { cancellableContinuation ->
            val reference: StorageReference =
                storage.reference.child("download/${uri.lastPathSegment}")
            reference.putFile(uri).addOnSuccessListener {
                downloadImage(it, cancellableContinuation)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        }
    }


     */
    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot, cancellableContinuation: CancellableContinuation<Uri>
    ) {
        uploadTask.storage.downloadUrl
            .addOnSuccessListener { uri -> cancellableContinuation.resume(uri) }
            .addOnFailureListener { cancellableContinuation.resumeWithException(it) }
    }

    suspend fun downloadBasicImage(): Uri {
        // val reference = storage.reference.child("$userId/profile.png")  en el cado que queramos cargar la foto de un usuario para el perfil
        val reference = storage.reference.child("ejemplo/Cadiz.jpg")

        /*
        De esta forma no se forma una uri, se forma una tags
        reference.downloadUrl.addOnSuccessListener { Log.i("bien", "succes") }
            .addOnFailureListener { Log.i("mal", "fail") }
         */

        return reference.downloadUrl.await()

    }



    suspend fun removeImage(imageName: String): Boolean {
        return try {
            val reference: StorageReference = storage.reference.child(imageName)
            // Verificar si el objeto existe antes de intentar eliminarlo
            val metadata = reference.metadata.await()
            if (metadata != null) {
                reference.delete().await()
                true
            } else {
                // El objeto no existe
                false
            }
        } catch (e: Exception) {
            // Manejar cualquier otra excepción
            // Log.e("removeImage", "Error removing image: ${e.message}")
            false
        }
    }

    /*
    suspend fun removeImage(imageName: String): Boolean {
        return try {
            val reference: StorageReference = storage.reference.child(imageName)
            reference.delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

     */

    suspend fun readMetadata(referencia: String): String? {
        val storage = Firebase.storage
        val reference = storage.reference.child(referencia)

        try {
            val response: StorageMetadata = reference.metadata.await()
            val mimeType: String? = response.contentType
            return when {
                mimeType == "image/jpeg" -> "image/jpeg"
                mimeType == "video/mp4" -> "video/mp4"
                else -> "Tipo desconocido"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /*
    suspend fun readMetadata(referencia: String): String?{
        val reference: StorageReference = storage.reference.child(referencia)
        val response: StorageMetadata = reference.metadata.await()
        val metainfo: String? = response.getCustomMetadata("Tipo")
        return metainfo
    }

     */




    suspend fun getAllImages(): List<Uri>{
        val reference: StorageReference = storage.reference.child("picture/")
        /*
        reference.listAll().addOnSuccessListener{ result ->
            result.items.forEach{
                Log.i("Todas las imagenes", it.name)
            }
        }
         */
        val result: List<Uri> = reference.listAll().await().items.map{it.downloadUrl.await()}
        return result
    }

    suspend fun uploadVideoAndGetDownloadUrl(uri: Uri): Uri {
        return suspendCancellableCoroutine { continuation ->
            val reference: StorageReference = storage.reference.child("videos/${uri.lastPathSegment}")
            reference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    continuation.resume(downloadUri)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    /*
    suspend fun downloadVideo(videoName: String): Uri {
        return suspendCancellableCoroutine { continuation ->
            val reference: StorageReference = storage.reference.child("videos/$videoName")
            val localFile = File.createTempFile("temp", "mp4")
            reference.getFile(localFile).addOnSuccessListener {
                continuation.resume(Uri.fromFile(localFile))
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

     */

}

