package com.example.cleanarqu.Cocktails.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarqu.Cocktails.Domain.NameUseCase
import com.example.cleanarqu.Cocktails.Model.CocktailUser
import com.example.cleanarqu.Cocktails.Model.Response.User
import com.example.cleanarqu.Cocktails.ui.State.drinkState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import com.example.cleanarqu.Cocktails.Domain.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.cleanarqu.Cocktails.Domain.RandomCocktail
import com.example.cleanarqu.Cocktails.Model.ListUiState
import com.example.cleanarqu.Cocktails.Model.MainScreenState
import com.example.cleanarqu.Cocktails.Model.StorageService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import kotlin.Exception


@HiltViewModel
class Viewmodel @Inject constructor(private val nameUseCase: NameUseCase, private val randomCocktail: RandomCocktail,
    private val storageService: StorageService, /*private val locations: LocationUseCase*/): ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

    //private val _games = MutableStateFlow<List<GameInfoState>>(emptyList())
    //val games: StateFlow<List<GameInfoState>> = _games.asStateFlow()
    private val _nombre = MutableStateFlow<List<drinkState>>(emptyList())
    val nombre: StateFlow<List<drinkState>> = _nombre.asStateFlow()

    private val _cocktailData = MutableStateFlow<List<CocktailUser>>(emptyList())
    val cocktailData: StateFlow<List<CocktailUser>> = _cocktailData

    var _ingredient by mutableStateOf("")
        private set

    var id by mutableStateOf("")
        private set

    var uriFoto by mutableStateOf("")
        private set

    var uriVideo by mutableStateOf("")
        private set

    var descripcion by mutableStateOf("")
        private set

    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _show = mutableStateOf<Boolean>(false)
    val show: MutableState<Boolean> = _show

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var userName by mutableStateOf("")
        private set

    var imageName by mutableStateOf("")
        private set

    var videoName by mutableStateOf("")
        private set

    /*
    var listaIngredientes by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

     */

    var listaIngredientes by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

    var cocktailName by mutableStateOf("")
        private set

    var drink by mutableStateOf(CocktailUser())
        private set

    var showAlert by mutableStateOf(false)
        private set


    var _selectedRowId by mutableStateOf<String?>(null)
        private set

    //var _uri by mutableStateOf<Uri?>(null)
    //private set

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri: StateFlow<Uri?> = _uri

    var resultUri by mutableStateOf<Uri?>(null)
        private set

    var resultUriVideo by mutableStateOf<Uri?>(null)
        private set

    var namePhoto by mutableStateOf("")
        private set

    var ingrediente by mutableStateOf("")
        private set

    var metada by mutableStateOf("")
        private set

    var nameCockFood by mutableStateOf("")
        private set

    var focusRequest by mutableStateOf(FocusRequester())

    private var _uiState = MutableStateFlow(ListUiState(false, emptyList()))
    val uiState: StateFlow<ListUiState> = _uiState

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    var actionTranslate by mutableStateOf(true)
        private set

    var puntuacion by mutableStateOf(0.0)
        private set

    var votes by mutableStateOf(0)
        private set

    var currentRating by mutableStateOf(0.0)
        private set

    var listVotes by mutableStateOf(listOf<Double>())
        private set

    var averageRating by mutableStateOf(0.0)
        private set

    var idDoc by mutableStateOf("")
        private set

    private val _currentLocation = MutableLiveData<Pair<Double, Double>>()
    val currentLocation: LiveData<Pair<Double, Double>> = _currentLocation

    /*
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

     */


    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location

    private val _savedLocation = MutableStateFlow<LatLng?>(null)
    val savedLocation: StateFlow<LatLng?> = _savedLocation


    fun getName(name: String) {
        viewModelScope.launch {
            _nombre.value = nameUseCase(name).drinks ?: mutableListOf()
        }
    }

    fun getRandom() {
        viewModelScope.launch {
            _nombre.value = randomCocktail().drinks ?: mutableListOf()
        }
    }

    fun IngredientsUser(ingredientes: CocktailUser): String {
        _ingredient = ""
        for (ingrediente in ingredientes.strList!!) {
            if (ingrediente.equals("vacio")) break else _ingredient += "$ingrediente "
        }
        return _ingredient
    }


    fun Ingredients(ingredientes: drinkState): String {
        _ingredient = ""

        for (ingrediente in ingredientes.strIngredient!!) {
            if (ingrediente.equals("vacio")) break else _ingredient += "$ingrediente "
        }
        /*
        val lista = mutableListOf<String>(ingredientes.strIngredient1?: "vacio", ingredientes.strIngredient2?: "vacio", ingredientes.strIngredient3?: "vacio", ingredientes.strIngredient4?: "vacio",
                ingredientes.strIngredient5?: "vacio", ingredientes.strIngredient6?: "vacio", ingredientes.strIngredient7?: "vacio", ingredientes.strIngredient8?: "vacio",
                ingredientes.strIngredient9?: "vacio", ingredientes.strIngredient10?: "vacio", ingredientes.strIngredient11?: "vacio", ingredientes.strIngredient12?: "vacio",
                ingredientes.strIngredient3?: "vacio", ingredientes.strIngredient14?: "vacio", ingredientes.strIngredient15?: "vacio")
        for(ingrediente in lista){
            if(ingrediente.equals("vacio")) break else _ingredient += "$ingrediente "
        }

         */


        return _ingredient
    }


    fun calculateBackgroundColor(index: Int): Color {
        return if (index % 2 == 0) Color(0xFFF9EBE0) else Color.White
    }


    fun SaveCocktail(
        idDrink: String,
        strDrink: String,
        strInstructions: String,
        strDrinkThumb: String,
        strList: MutableList<String>,
        strMedia: String?,
        imageName: String?,
        videoName: String
    ) {

        val email = auth.currentUser?.email

        listaIngredientes.clear()

        for(ingrediente in strList){
            if(ingrediente.equals("")) continue else listaIngredientes.add(ingrediente)
        }


        //listaIngredientes = strList
        drink = CocktailUser(
            email.toString(),
            idDrink,
            strDrink,
            strInstructions,
            strDrinkThumb,
            listaIngredientes,
            strMedia,
            imageName,
            videoName
        )
    }

    fun createCocktail(
        idDrink: String,
        strDrink: String,
        strInstructions: String,
        strDrinkThumb: String,
        strList: List<String>?,
        strMedia: String
    ) {
        val email = auth.currentUser?.email

        for(ingrediente in strList?: emptyList()){
            if(ingrediente.equals("vacio")) continue else listaIngredientes.add(ingrediente)
        }


        //listaIngredientes = strList
        drink = CocktailUser(
            email.toString(),
            idDrink,
            strDrink,
            strInstructions,
            strDrinkThumb,
            listaIngredientes,
            strMedia
        )
    }


    fun mostrar() {
        showAlert = !showAlert
    }


    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // DCS - Utiliza el servicio de autenticación de Firebase para validar al usuario
                // por email y contraseña
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Usuario y/o contrasena incorrectos")
                            showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }


    private fun saveUser(username: String) {
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = User(
                id = id.toString(),
                email = email.toString(),
                name = username
            )
            // DCS - Añade el usuario a la colección "Users" en la base de datos Firestore
            firestore.collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d(
                        "GUARDAR OK",
                        "Se guardó el usuario correctamente en Firestore"
                    )
                }
                .addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }


    fun createUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // DCS - Utiliza el servicio de autenticación de Firebase para registrar al usuario
                // por email y contraseña
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // DCS - Si se realiza con éxito, almacenamos el usuario en la colección "Users"
                            saveUser(userName)
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Error al crear usuario")
                            showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR CREAR USUARIO", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    fun saveNewCocktail(onSuccess: () -> Unit) {
        //val email = auth.currentUser?.email
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Verificar si el cocktail ya existe en la base de datos
                firestore.collection("Cocktails")
                    .whereEqualTo("idDrink", drink.idDrink)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (querySnapshot.isEmpty) {
                            // No existe ningún cocktail con el mismo IdDrink, entonces lo guardamos
                            firestore.collection("Cocktails")
                                .add(drink)
                                .addOnSuccessListener {
                                    onSuccess()
                                    Log.d(
                                        "GUARDAR OK",
                                        "Se guardó el cocktail correctamente en Firestore"
                                    )
                                }
                                .addOnFailureListener {
                                    Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore")
                                }
                        } else {
                            // Ya existe un cocktail con el mismo IdDrink, no hacemos nada
                            Log.d("COCKTAIL REPETIDO", "El cocktail ya existe en Firestore")
                        }
                    }
                    .addOnFailureListener {
                        Log.d(
                            "ERROR AL VERIFICAR",
                            "Error al verificar si el cocktail existe en Firestore"
                        )
                    }
            } catch (e: Exception) {
                Log.d("ERROR GUARDAR COCKTAIL", "Error al guardar ${e.localizedMessage}")
            }
        }
    }

    fun fetchCoctail() {
        val email = auth.currentUser?.email

        // DCS - addSnapshotListener ya trae todas las funciones necesarias para la concurrencia
        // de datos y es asíncrono, por lo que no es necesario introducir el viewModelScope.
        // Ya lleva incluida todas las corrutinas necesarias...
        firestore.collection("Cocktails")
            .whereEqualTo("emailUser", email.toString())
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val documents = mutableListOf<CocktailUser>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val myDocument =
                            document.toObject(CocktailUser::class.java).copy(idDocument = document.id)
                        documents.add(myDocument)
                    }
                }
                _cocktailData.value = documents
            }
    }

    fun getCoctailById(documento: String){
        firestore.collection("Cocktails")
            .document(documento)
            .addSnapshotListener { querySnapshot, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                if(querySnapshot != null){
                    val cocktail = querySnapshot.toObject(CocktailUser::class.java)
                    drink = drink.copy(
                        idDrink = cocktail?.idDrink,
                        strDrink = cocktail?.strDrink,
                        strInstructions = cocktail?.strInstructions,
                        strDrinkThumb = cocktail?.strDrinkThumb,
                        strList = cocktail?.strList,
                        strmedia = cocktail?.strmedia,
                        puntuacion = cocktail?.puntuacion ,
                        votes = cocktail?.votes
                    )
                }
            }
    }

    fun updateStars(iDoc: String, onSuccess:() -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val plusVotes = hashMapOf(
                    "puntuacion" to drink.puntuacion,
                    "votes" to drink.votes
                )
                firestore.collection("Cocktails").document(iDoc)
                    .update(plusVotes as Map<String, Any>)
                    .addOnSuccessListener {
                        onSuccess()
                        Log.d("Actualizacion OK", "Se ha actualizado correctamente")
                    }
                    .addOnFailureListener {
                        Log.d("Error al actualizar", "No se ha podido realizar la actualización.")
                    }
                cleanVotes()
            }catch (e: Exception){
                Log.d("Error subir", "Error subir puntos")
            }
        }
    }

    fun deleteCocktail(documento: String, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                firestore.collection("Cocktails")
                    .document(documento)
                    .delete()
                    .addOnSuccessListener {
                        onSuccess()
                        Log.d("Exito al borrar", "Cocktail borrado con éxito")
                    }
                    .addOnFailureListener{
                        Log.d("Error al borrar", "No se pudo borrar el cocktail.")
                    }
            }catch(e: Exception){
                Log.d("Error al borrar cocktail", "Error ${e.localizedMessage}")
            }
        }
    }

    fun uploadBasicImage(uri: Uri) {
        storageService.uploadBasicImage(uri)
    }

    fun uploadAndGetImage(uri: Uri, onSuccessDownload: (Uri) -> (Unit)) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: Uri =
                    withContext(Dispatchers.IO) { storageService.uploadAndDownloadImage(uri) }
                onSuccessDownload(result)
            } catch (e: Exception) {
                Log.i("error", e.message.orEmpty())
            }
            _isLoading.value = false
        }
    }

    fun uploadAndGetVideo(uri: Uri, onSuccessDownload: (Uri) -> (Unit)) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: Uri =
                    withContext(Dispatchers.IO) { storageService.uploadVideoAndGetDownloadUrl(uri) }
                onSuccessDownload(result)
            } catch (e: Exception) {
                Log.i("error", e.message.orEmpty())
            }
            _isLoading.value = false
        }
    }

    /*
    var intentCameraLaucher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
        if(it && uri?.path?.isNotEmpty() == true){
            //viewmodel.uploadBasicImage(uri!!)
            viewmodel.uploadAndGetImage(uri!!){ newUri -> resultUri = newUri}
        }
    }
     */


    /*
    Para capturar videos
    @Composable
    fun intentCameraLaucher(result: Uri?, focusManager: FocusManager, ): ManagedActivityResultLauncher<Uri,Boolean>{
        return rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()){
            if(it && result?.path?.isNotEmpty() == true){
                //viewmodel.uploadBasicImage(uri!!)
                uploadAndGetImage(result){ newUri ->
                    namePhoto = ""
                    focusManager.clearFocus()
                    resultUri = newUri
                }
            }
        }
    }
     */

    @Composable
    fun intentCameraLaucher(
        result: Uri?,
        focusManager: FocusManager, ): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it && result?.path?.isNotEmpty() == true) {
                //viewmodel.uploadBasicImage(uri!!)
                uploadAndGetImage(result) { newUri ->
                    namePhoto = ""
                    focusManager.clearFocus()
                    resultUri = newUri
                    uriFoto = newUri.toString()
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }

    @Composable
    fun intentCameraLaucherVideo(
        result: Uri?,
        focusManager: FocusManager, ): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) {
            if (it && result?.path?.isNotEmpty() == true) {
                //viewmodel.uploadBasicImage(uri!!)
                uploadAndGetVideo(result) { newUri ->
                    namePhoto = ""
                    focusManager.clearFocus()
                    resultUri = newUri
                    uriVideo = newUri.toString()
                    videoName = newUri.lastPathSegment.toString()
                    getMetadata(videoName)
                }
            }
        }
    }






    @Composable
    fun intentGalleryLaucher(): ManagedActivityResultLauncher<String, Uri?>{
        return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
            if(it?.path?.isNotEmpty() == true){
                //viewmodel.uploadBasicImage(it)
                uploadAndGetImage(it){ newUri ->
                    resultUri = newUri
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }


    fun choose(metadata: String){
        when (metadata) {
            "image/jpeg" -> {

                changeUriFoto(resultUri.toString())
            }
            "video/mp4" -> {
                changeUriVideo(resultUri.toString())
            }
        }
    }


    /*
        imageName = newUri.lastPathSegment.toString()
                        val metadato = getMetadata(imageName)
                        if(metadato.equals("image/jpeg")){
                            resultUri = newUri
                            uriVideo = newUri.toString()
                        }else uriFoto = newUri.toString()
         */

    fun getMetadata(reference: String){
        viewModelScope.launch {
            metada = storageService.readMetadata(reference)?: ""
        }
    }

    fun generateUri(context: ComponentActivity, nombre: String, sufijo: String): Uri {
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            "com.example.cleanarqu.provider",
            createFile(context, nombre, sufijo)
        )
    }


    fun createFile(context: ComponentActivity, nombre: String ,sufijo: String): File {
        val name: String = nombre.ifEmpty {SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())+"image"}
        return File.createTempFile(name,sufijo, context.externalCacheDir)
    }


    fun getAllImages(){
        viewModelScope.launch{
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result: List<String> = withContext(Dispatchers.IO){
                storageService.getAllImages().map{it.toString()}
            }
            _uiState.value = _uiState.value.copy(isLoading = false, images = result)
        }
    }

    fun deleteImage(imageName: String, context: ComponentActivity) {
        viewModelScope.launch {
            val success = storageService.removeImage(imageName)
            if (success) {
                // Si la eliminación es exitosa, muestra un mensaje de confirmación
                showMessage(context, "Imagen eliminada satisfactoriamente")
            } else {
                // Si la eliminación falla, muestra un mensaje de error
                showMessage(context, "No se pudo eliminar la imagen")
            }
        }
    }

    fun showMessage(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("Aceptar", null) // Aquí puedes agregar un OnClickListener si lo necesitas
            .show()
    }

    fun onTextToBeTranslatedChange(text: String){
        _state.value = state.value.copy(textToBeTranslate = text)
    }


    fun onTranslateButtonClick(text: String, context: Context){
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH)
            .build()

        val languageTranslator = Translation
            .getClient(options)

        languageTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
             _state.value = state.value.copy(
                 translatedText = translatedText
             )
            }
            .addOnFailureListener {
                Toast.makeText(context,"traducción en curso",Toast.LENGTH_SHORT).show()
                downloadModelIfNotAviable(languageTranslator, context)
            }
    }



    private fun downloadModelIfNotAviable(languageTranslator: Translator, conext: Context){
        _state.value = state.value.copy(
            isButtonEnabled = false
        )

        val conditions = DownloadConditions
            .Builder()
            .requireWifi()
            .build()

        languageTranslator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                _state.value = state.value.copy(
                    isButtonEnabled = true
                )
            }
            .addOnFailureListener {
                Toast.makeText(conext,"No se ha podidio traducir",Toast.LENGTH_SHORT).show()
            }
    }




    fun calculateAverageRating(){
        averageRating = if (listVotes.isNotEmpty()) {
            listVotes.sum() / listVotes.size
        } else {
            0.0
        }
    }

    /*
    @RequiresApi(Build.VERSION_CODES.S)
    fun getLocation(){
        viewModelScope.launch {
            _location.value = locations.invoke()
        }
    }

     */


    @SuppressLint("MissingPermission")
    fun requestLocation(context: ComponentActivity) {
        viewModelScope.launch {
            val location = getUserLocation(context)
            _location.value = location?.let { LatLng(it.latitude, it.longitude) }
        }
    }

    fun saveLocation(location: LatLng) {
        _savedLocation.value = location
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(context: ComponentActivity): Location? {
        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGPSEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result) {}
                    } else {
                        cont.resume(null) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) {}
                }
                addOnFailureListener {
                    cont.resume(null) {}
                }
                addOnCanceledListener {
                    cont.resume(null) {}
                }
            }
        }
    }



    /*
    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(context: ComponentActivity): Location?{
        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val isUserLocationPermissionsGranted = true
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER)

        if(!isGPSEnabled || !isUserLocationPermissionsGranted){
            return null
        }

        return suspendCancellableCoroutine { cont ->

            /*
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            )

             */


                fusedLocationProviderClient.lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            cont.resume(result) {}
                        }else{
                            cont.resume(null){}
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        cont.resume(it){}
                    }
                    addOnFailureListener{
                        cont.resume(null){}
                    }
                    addOnCanceledListener { cont.resume(null){} }
                }
        }
    }


     */


    /*
     fun changeLocation(lo: Location?){
         _location.value = lo
     }

     */

    fun setCurrentLocation(latitude: Double, longitude: Double) {
        _currentLocation.value = Pair(latitude, longitude)
    }

    fun calculateAverage(votes: Int, valueVote: Double): Double = valueVote/votes


    fun cockApiOrUser(election: String){
        if(election == "api") retuCockApi() else retuCockUser()
    }

    fun retuCockApi(): List<drinkState>{
        return _nombre.value
    }

    fun retuCockUser():List<CocktailUser>{
        return _cocktailData.value
    }

    fun closeAlert(){
        showAlert = false
    }

    /**
     * Actualiza el email del usuario.
     *
     * @param email Nuevo email a establecer.
     */
    fun changeEmail(email: String) {
        this.email = email
    }

    /**
     * Actualiza la contraseña del usuario.
     *
     * @param password Nueva contraseña a establecer.
     */
    fun changePassword(password: String) {
        this.password = password
    }

    fun changeNameCocktail(name:String){
        this.cocktailName = name
    }
    /**
     * Actualiza el nombre de usuario.
     *
     * @param userName Nuevo nombre de usuario a establecer.
     */
    fun changeUserName(userName: String) {
        this.userName = userName
    }

    fun lightRow(vista: Boolean){
        _show.value = !vista
    }

    fun changeSelectedRow(id: String?) {
        _selectedRowId = id
    }

    fun changeColorRow(row: String?): Color{
        return if(_selectedRowId == row)Color.Red else Color.White
    }

    /*
    fun changeUri(context: ComponentActivity){
        _uri.value = generateUri(context)
    }

     */

    fun changeResultUri(result: Uri){
        resultUri = result
    }

    fun changeResultUriVideo(result: Uri){
        resultUriVideo = result
    }

    fun changeNamePhoto(name: String){
        namePhoto = name
    }


    fun changeDrink(cocktail: CocktailUser){
        drink = cocktail
    }

    fun changeId(idCocktail: String){
        id = idCocktail
    }

    fun changeUriFoto(uriFo: String){
        uriFoto = uriFo
    }

    fun changeUriVideo(uriVi: String){
        uriVideo = uriVi
    }

    fun changeDescripcion(desc: String){
        descripcion = desc
    }

    fun changeIngrediente(ingredient: String){
        ingrediente = ingredient
    }

    fun changeNamecockFood(name: String){
        nameCockFood = name
    }
    fun addIngrediente(ingre: String){
        listaIngredientes?.plus(ingre)
    }

    fun changeMetada(meta: String){
        metada = meta
    }

    fun changeActionTranslate(value: Boolean){
        actionTranslate = value
    }

    fun cleanVotes(){
        puntuacion = 0.0
        votes = 0
        currentRating = 0.0
    }

    fun updateListVotes(newRating: Double){
        listVotes = listVotes + newRating
    }

    fun plusVotes(currentVotes: Int){
        votes += currentVotes
    }

    fun plusPuntuacion(currentPuntacion: Double){
        puntuacion += currentPuntacion
    }

    fun changeValue(vote: Int){
        votes = vote
    }

    fun changeValuePoint(point: Double){
        puntuacion = point
    }

    fun changeCurrentRating(current: Double){
        currentRating = current
    }

    fun changeValueVotes(value: Double, text: String){
        when(text){
            "puntuacion" -> drink = drink.copy(puntuacion = value + drink.puntuacion!! )
            "votes" -> drink = drink.copy(votes = drink.votes!! + value.toInt())
        }
    }

    fun changeIdDocument(id: String){
        idDoc = id
    }

    /*
    fun onValueChange(value:String, text: String) {
        when(text){
            "title" -> state = state.copy(title = value)
            "note" -> state = state.copy(note = value)
        }
    }


     */
}
