package com.example.peliculaspopulares.model

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peliculaspopulares.PeliculasApplication
import com.example.peliculaspopulares.data.listasdao.PeliculasNowDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasPopularDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasTopDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasUpcomingDAO
import com.example.peliculaspopulares.data.listasdaoid.PeliculasNowDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasPopularDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasTopDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasUpcomingDAOID
import com.example.peliculaspopulares.repositorio.local.PeliculaRepositoryDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface MoviesPopularUiStateDao {
    data class Success(val photos: List<PeliculasPopularDAO>) : MoviesPopularUiStateDao
    object Error : MoviesPopularUiStateDao
    object Loading : MoviesPopularUiStateDao
}

sealed interface MoviesNowUiStateDao {
    data class Success(val photos: List<PeliculasNowDAO>) : MoviesNowUiStateDao
    object Error : MoviesNowUiStateDao
    object Loading : MoviesNowUiStateDao
}

sealed interface MoviesTopUiStateDao {
    data class Success(val photos: List<PeliculasTopDAO>) : MoviesTopUiStateDao
    object Error : MoviesTopUiStateDao
    object Loading : MoviesTopUiStateDao
}

sealed interface MoviesUpcomingUiStateDao {
    data class Success(val photos: List<PeliculasUpcomingDAO>) : MoviesUpcomingUiStateDao
    object Error : MoviesUpcomingUiStateDao
    object Loading : MoviesUpcomingUiStateDao
}

data class MoviesPopularDao(val photos: List<PeliculasPopularDAO> = emptyList())

data class MoviesNowDao(val photos: List<PeliculasNowDAO> = emptyList())

data class MoviesTopDao(val photos: List<PeliculasTopDAO> = emptyList())

data class MoviesUpcomingDao(val photos: List<PeliculasUpcomingDAO> = emptyList())

sealed interface MoviesPopularUiStateIdDao {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
        //val genero: List<Generodetalles>,
                       val poster: String) : MoviesPopularUiStateIdDao
    object Error : MoviesPopularUiStateIdDao
    object Loading : MoviesPopularUiStateIdDao
}

sealed interface MoviesNowUiStateIdDao {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
        //val genero: List<Generodetalles>,
                       val poster: String) : MoviesNowUiStateIdDao
    object Error : MoviesNowUiStateIdDao
    object Loading : MoviesNowUiStateIdDao
}

sealed interface MoviesTopUiStateIdDao {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
        //val genero: List<Generodetalles>,
                       val poster: String) : MoviesTopUiStateIdDao
    object Error : MoviesTopUiStateIdDao
    object Loading : MoviesTopUiStateIdDao
}

sealed interface MoviesUpcomingUiStateIdDao {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
        //val genero: List<Generodetalles>,
                       val poster: String) : MoviesUpcomingUiStateIdDao
    object Error : MoviesUpcomingUiStateIdDao
    object Loading : MoviesUpcomingUiStateIdDao
}

data class MoviesPopularUiStateIdDaoDetalle(
    var id: String = "",
    var titulo: String = "",
    var descipcion: String = "",
    var fechalanzamiento: String = "",
    var porcenjatevotos: Float = 0f,
    var lenguaje: String = "",
    //val genero: List<Generodetalles> = emptyList(),
    var poster: String = "")

data class MoviesNowUiStateIdDaoDetalle(
    var id: String = "",
    var titulo: String = "",
    var descipcion: String = "",
    var fechalanzamiento: String = "",
    var porcenjatevotos: Float = 0f,
    var lenguaje: String = "",
    //val genero: List<Generodetalles> = emptyList(),
    var poster: String = "")

data class MoviesTopUiStateIdDaoDetalle(
    var id: String = "",
    var titulo: String = "",
    var descipcion: String = "",
    var fechalanzamiento: String = "",
    var porcenjatevotos: Float = 0f,
    var lenguaje: String = "",
    //val genero: List<Generodetalles> = emptyList(),
    var poster: String = "")

data class MoviesUpcomingUiStateIdDaoDetalle(
    var id: String = "",
    var titulo: String = "",
    var descipcion: String = "",
    var fechalanzamiento: String = "",
    var porcenjatevotos: Float = 0f,
    var lenguaje: String = "",
    //val genero: List<Generodetalles> = emptyList(),
    var poster: String = "")

class PeliculaDaoViewModel (private val repository: PeliculaRepositoryDao) : ViewModel() {

    /**   // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    private val _allPelis = repository.allPeliculas.asLiveData()
    private val _descripcion = MutableLiveData<String>()
    private val _fechalanzamiento = MutableLiveData<String>()
    private val _porcentajevotos = MutableLiveData<Float>()
    private val _lenguaje = MutableLiveData<String>()
    private val _backdrop = MutableLiveData<String>()

    val allPelis : LiveData<List<PeliculasPopularDAO>> = _allPelis
    val descripcion : LiveData<String> = _descripcion
    val fchalanzamiento : LiveData<String> = _fechalanzamiento
    val porcentajevotos : LiveData<Float> = _porcentajevotos
    val lenguaje : LiveData<String> = _lenguaje
    val backdrop :LiveData<String> = _backdrop

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
    */
    fun insert(peliculas: List<PeliculasPopularDAO>) = viewModelScope.launch {
    try {
    repository.insert(peliculas)
    }catch (e:Exception){}
    }

    fun insertID(pelicula: List<PeliculasPopularDAOID>) = viewModelScope.launch {
    try {
    repository.intertPeli(pelicula)
    }catch (e:Exception){}
    }

    fun datoDetalle(data : String)  = viewModelScope.launch {
    try {
    repository.datoDetalle(data).collect { data ->

    _descripcion.value = data.descipcion
    _fechalanzamiento.value = data.fechalanzamiento
    _lenguaje.value = data.lenguaje
    _porcentajevotos.value = data.porcenjatevotos
    _backdrop.value = data.poster

    }}catch (e:Exception){}
    }
     */

    var moviesPopularUiState: MoviesPopularUiStateDao by mutableStateOf(MoviesPopularUiStateDao.Loading)
        private set
    var moviesNowUiState: MoviesNowUiStateDao by mutableStateOf(MoviesNowUiStateDao.Loading)
        private set
    var moviesTopUiState: MoviesTopUiStateDao by mutableStateOf(MoviesTopUiStateDao.Loading)
        private set
    var moviesUpcomingUiState: MoviesUpcomingUiStateDao by mutableStateOf(MoviesUpcomingUiStateDao.Loading)
        private set
    var moviesPopularUiStateId: MoviesPopularUiStateIdDao by mutableStateOf(MoviesPopularUiStateIdDao.Loading)
        private set
    var moviesNowUiStateId: MoviesNowUiStateIdDao by mutableStateOf(MoviesNowUiStateIdDao.Loading)
        private set
    var moviesTopUiStateId: MoviesTopUiStateIdDao by mutableStateOf(MoviesTopUiStateIdDao.Loading)
        private set
    var moviesUpcomingUiStateId: MoviesUpcomingUiStateIdDao by mutableStateOf(MoviesUpcomingUiStateIdDao.Loading)
        private set


    init {

        getMoviesPopular()

        getMoviesNow()

        getMoviesTop()

        getMoviesUpcoming()
        //x()
    }

    private val _moviesPopularUiStateIdDaoDetalle = MutableStateFlow(MoviesPopularUiStateIdDaoDetalle())
    private val _moviesNowUiStateIdDaoDetalle = MutableStateFlow(MoviesNowUiStateIdDaoDetalle())
    private val _moviesTopUiStateIdDaoDetalle = MutableStateFlow(MoviesTopUiStateIdDaoDetalle())
    private val _moviesUpcomingUiStateIdDaoDetalle = MutableStateFlow(MoviesUpcomingUiStateIdDaoDetalle())
    private val _moviesPopularUiStateDao = MutableStateFlow(MoviesPopularDao())
    private val _moviesNowUiStateDao = MutableStateFlow(MoviesNowDao())
    private val _moviesTopUiStateDao = MutableStateFlow(MoviesTopDao())
    private val _moviesUpcomingUiStateDao = MutableStateFlow(MoviesUpcomingDao())

    val moviesPopularUiStateIdDaoDetallee: StateFlow<MoviesPopularUiStateIdDaoDetalle> = _moviesPopularUiStateIdDaoDetalle.asStateFlow()
    val moviesNowUiStateIdDaoDetallee: StateFlow<MoviesNowUiStateIdDaoDetalle> = _moviesNowUiStateIdDaoDetalle.asStateFlow()
    val moviesTopUiStateIdDaoDetallee: StateFlow<MoviesTopUiStateIdDaoDetalle> = _moviesTopUiStateIdDaoDetalle.asStateFlow()
    val moviesUpcomingUiStateIdDaoDetallee: StateFlow<MoviesUpcomingUiStateIdDaoDetalle> = _moviesUpcomingUiStateIdDaoDetalle.asStateFlow()
    val moviesPopularUiStateDao: StateFlow<MoviesPopularDao> = _moviesPopularUiStateDao.asStateFlow()
    val moviesNowUiStateDao: StateFlow<MoviesNowDao> = _moviesNowUiStateDao.asStateFlow()
    val moviesTopUiStateDao: StateFlow<MoviesTopDao> = _moviesTopUiStateDao.asStateFlow()
    val moviesUpcomingUiStateDao: StateFlow<MoviesUpcomingDao> = _moviesUpcomingUiStateDao.asStateFlow()

    fun deletePopular() = viewModelScope.launch {
        try {
            repository.deletePopular()

        }catch (e:Exception){}
    }

    fun deleteNow() = viewModelScope.launch {
        try {
            repository.deleteNow()

        }catch (e:Exception){}
    }

    fun deleteTop() = viewModelScope.launch {
        try {
            repository.deleteTop()

        }catch (e:Exception){}
    }

    fun deleteUpcoming() = viewModelScope.launch {
        try {
            repository.deleteUpcoming()

        }catch (e:Exception){}
    }

    fun deletePopularId() = viewModelScope.launch {
        try {
            repository.deletePopularId()
        } catch (e:Exception){
        }
    }

    fun deleteNowId() = viewModelScope.launch {
        try {
            repository.deleteNowId()
        } catch (e:Exception){
        }
    }

    fun deleteTopId() = viewModelScope.launch {
        try {
            repository.deleteTopId()
        } catch (e:Exception){
        }
    }

    fun deleteUpcomingId() = viewModelScope.launch {
        try {
            repository.deleteUpcomingId()
        } catch (e:Exception){
        }
    }

    /**
     * Holds current item ui state
     */
    /**fun x() {

    moviesUiState = MoviesPopularUiStateDao.Success(moviesUiStateDao.value.photos)
    }*/
    @SuppressLint("SuspiciousIndentation")
    fun getMoviesPopular() {
        viewModelScope.launch {
            try{
                repository.getMoviesPopularStream().collect { data ->
                    moviesPopularUiState = MoviesPopularUiStateDao.Success(photos = data)
                    //repository.getMoviesStream().collect { data ->
                    //_moviesUiStateDao.value = MoviesPopularDao(photos = data.toList())
                }
            }catch (e:Exception){}
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getMoviesNow() {
        viewModelScope.launch {
            try{
                repository.getMoviesNowStream().collect { data ->
                    moviesNowUiState = MoviesNowUiStateDao.Success(photos = data)
                    //repository.getMoviesStream().collect { data ->
                    //_moviesUiStateDao.value = MoviesPopularDao(photos = data.toList())
                }
            }catch (e:Exception){}
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getMoviesTop() {
        viewModelScope.launch {
            try{
                repository.getMoviesTopStream().collect { data ->
                    moviesTopUiState = MoviesTopUiStateDao.Success(photos = data)
                    //repository.getMoviesStream().collect { data ->
                    //_moviesUiStateDao.value = MoviesPopularDao(photos = data.toList())
                }
            }catch (e:Exception){}
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun getMoviesUpcoming() {
        viewModelScope.launch {
            try{
                repository.getMoviesUpcomingStream().collect { data ->
                    moviesUpcomingUiState = MoviesUpcomingUiStateDao.Success(photos = data)
                    //repository.getMoviesStream().collect { data ->
                    //_moviesUiStateDao.value = MoviesPopularDao(photos = data.toList())
                }
            }catch (e:Exception){}
        }
    }

    fun refreshMoviesPopularData(peliculas: List<PeliculasPopularDAO>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesPopular(peliculas)
            }  catch (e: IOException) {
            }
        }
    }

    fun refreshMoviesNowData(peliculas: List<PeliculasNowDAO>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesNow(peliculas)
            }  catch (e: IOException) {
            }
        }
    }

    fun refreshMoviesTopData(peliculas: List<PeliculasTopDAO>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesTop(peliculas)
            }  catch (e: IOException) {
            }
        }
    }

    fun refreshMoviesUpcomingData(peliculas: List<PeliculasUpcomingDAO>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesUpcoming(peliculas)
            }  catch (e: IOException) {
            }
        }
    }

    fun refreshMoviesPopularDataId(pelicula: List<PeliculasPopularDAOID>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesPopularId(pelicula)
            }catch (e:Exception){}
        }
    }

    fun refreshMoviesNowDataId(pelicula: List<PeliculasNowDAOID>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesNowId(pelicula)
            }catch (e:Exception){}
        }
    }

    fun refreshMoviesTopDataId(pelicula: List<PeliculasTopDAOID>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesTopId(pelicula)
            }catch (e:Exception){}
        }
    }

    fun refreshMoviesUpcomingDataId(pelicula: List<PeliculasUpcomingDAOID>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesUpcomingId(pelicula)
            }catch (e:Exception){}
        }
    }



    /**
    fun getMoviesId(data : String)  = viewModelScope.launch {
    try {
    repository.getMoviesPopularStreamId(data).collect { data ->

    moviesPopularUiStateId = MoviesPopularUiStateIdDao.Success(
    descipcion = data.descipcion,
    fechalanzamiento = data.fechalanzamiento,
    porcenjatevotos = data.porcenjatevotos,
    lenguaje = data.lenguaje,
    poster = data.poster,
    id = data.id,
    titulo = data.titulo
    )


    /** _moviesPopularUiStateIdDaoDetalle.value.descipcion = data.descipcion
    _moviesPopularUiStateIdDaoDetalle.value.fechalanzamiento = data.fechalanzamiento
    _moviesPopularUiStateIdDaoDetalle.value.lenguaje = data.lenguaje
    _moviesPopularUiStateIdDaoDetalle.value.porcenjatevotos = data.porcenjatevotos
    _moviesPopularUiStateIdDaoDetalle.value.poster = data.poster
    _moviesPopularUiStateIdDaoDetalle.value.id = data.id
    _moviesPopularUiStateIdDaoDetalle.value.titulo = data.titulo*/

    }}catch (e:Exception){}
    }







     */

    /** class PeliculaDaoViewModelFactory(private val repository: PeliculaRepositoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PeliculaDaoViewModel::class.java)) {
    @Suppress("UNCHECKED_CAST")
    return PeliculaDaoViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }

    }*/

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val repository = application.container.peliculaRepositoryDao
                PeliculaDaoViewModel(
                    repository = repository,
                )
            }
        }
    }
}




