package com.example.peliculaspopulares.model

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
import com.example.peliculaspopulares.data.Generodetalles
import com.example.peliculaspopulares.data.listas.PeliculasNow
import com.example.peliculaspopulares.data.listas.PeliculasPopular
import com.example.peliculaspopulares.data.listas.PeliculasTop
import com.example.peliculaspopulares.data.listas.PeliculasUpcoming
import com.example.peliculaspopulares.data.listasid.PeliculaNowID
import com.example.peliculaspopulares.data.listasid.PeliculaPopularID
import com.example.peliculaspopulares.data.listasid.PeliculaTopID
import com.example.peliculaspopulares.data.listasid.PeliculaUpcomingID
import com.example.peliculaspopulares.repositorio.network.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException


/**sealed interface MoviesUiState {
object Error : MoviesUiState
object Loading : MoviesUiState
data class Success(val photos: List<PeliculasPopular>) : MoviesUiState

}*/

data class MoviesListPopular(
    val movies: List<PeliculasPopular> = emptyList(),
    val isLoading: Boolean = false
)

data class MoviesListNow(
    val movies: List<PeliculasNow> = emptyList(),
    val isLoading: Boolean = false
)

data class MoviesListTop(
    val movies: List<PeliculasTop> = emptyList(),
    val isLoading: Boolean = false
)

data class MoviesListUpcoming(
    val movies: List<PeliculasUpcoming> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface MoviesUiStatePopularId {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
                       val genero: List<Generodetalles>,
                       val poster: String) : MoviesUiStatePopularId
    object Error : MoviesUiStatePopularId
    object Loading : MoviesUiStatePopularId
}

sealed interface MoviesUiStateNowId {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
                       val genero: List<Generodetalles>,
                       val poster: String) : MoviesUiStateNowId
    object Error : MoviesUiStateNowId
    object Loading : MoviesUiStateNowId
}

sealed interface MoviesUiStateTopId {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
                       val genero: List<Generodetalles>,
                       val poster: String) : MoviesUiStateTopId
    object Error : MoviesUiStateTopId
    object Loading : MoviesUiStateTopId
}

sealed interface MoviesUiStateUpcomingId {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
                       val genero: List<Generodetalles>,
                       val poster: String) : MoviesUiStateUpcomingId
    object Error : MoviesUiStateUpcomingId
    object Loading : MoviesUiStateUpcomingId
}

data class MoviesListPopularId(
    var movies: List<PeliculaPopularID> = emptyList()
)

data class MoviesListNowId(
    var movies: List<PeliculaNowID> = emptyList()
)

data class MoviesListTopId(
    var movies: List<PeliculaTopID> = emptyList()
)

data class MoviesListUpcomingId(
    var movies: List<PeliculaUpcomingID> = emptyList()
)

//@HiltViewModel
class PeliculasPopularesViewModel /**@Inject constructor*/(private val repository3: PeliculaRepository) : ViewModel() {

    /**
    //variables viewmodel

    private val _pelis = MutableLiveData<List<PeliculasPopular>>()
    private val _pelisfechalanzamiento = MutableLiveData<String>()
    private val _pelisid = MutableLiveData<String>()
    private val _peliscalificacion = MutableLiveData<Float>()
    private val _pelisgenero = MutableLiveData<List<Generodetalles>>()
    private val _pelislenguaje = MutableLiveData<String>()
    private val _errorconexion = MutableLiveData<String>()
    private val _poster = MutableLiveData<String>()

    private val _listapeliculasid = MutableLiveData<List<PeliculaPopularID>>()

    private val _pelisfechalanzamientodao = MutableLiveData<String>()
    private val _pelisiddao = MutableLiveData<String>()
    private val _pelistitulodao = MutableLiveData<String>()
    private val _pelisdescripciondao = MutableLiveData<String>()
    private val _peliscalificaciondao = MutableLiveData<Float>()
    private val _pelisgenerodao = MutableLiveData<List<Generodetalles>>()
    private val _pelislenguajedao = MutableLiveData<String>()
    private val _errorconexiondao = MutableLiveData<String>()
    private val _posterdao = MutableLiveData<String>()

    val pelis: LiveData<List<PeliculasPopular>> = _pelis
    val pelisfechalanzamiento : LiveData<String> = _pelisfechalanzamiento
    val pelisid: LiveData<String> = _pelisid
    val peliscalificacion : LiveData<Float> = _peliscalificacion
    val pelisgenero : LiveData<List<Generodetalles>> = _pelisgenero
    val pelislenguaje : LiveData<String> = _pelislenguaje
    val errorconexion : LiveData<String> = _errorconexion
    val poster : LiveData<String> = _poster
    val pelisfechalanzamientodao : LiveData<String> = _pelisfechalanzamientodao
    val pelisiddao: LiveData<String> = _pelisiddao
    val pelistitulodao: LiveData<String> = _pelistitulodao
    val pelisdescipciondao: LiveData<String> = _pelisdescripciondao
    val peliscalificaciondao : LiveData<Float> = _peliscalificaciondao
    val pelisgenerodao : LiveData<List<Generodetalles>> = _pelisgenerodao
    val pelislenguajedao : LiveData<String> = _pelislenguajedao
    val errorconexiondao : LiveData<String> = _errorconexiondao
    val posterdao : LiveData<String> = _posterdao


    val listapeliculasid : LiveData<List<PeliculaPopularID>> = _listapeliculasid

    fun getPeliculas() {

    viewModelScope.launch {
    try {

    _pelis.value = repository3.getPeliculas().body()?.results

    } catch (e: Exception) {

    }
    }
    }


    fun getPeliculaId(idpelicula: String) {

    viewModelScope.launch {

    try {

    _pelisid.value = repository3.getPeliculasID(idpelicula).body()?.descipcion
    _pelisfechalanzamiento.value = repository3.getPeliculasID(idpelicula).body()?.fechalanzamiento
    _peliscalificacion.value = repository3.getPeliculasID(idpelicula).body()?.porcenjatevotos
    _pelislenguaje.value = repository3.getPeliculasID(idpelicula).body()?.lenguaje
    _pelisgenero.value = repository3.getPeliculasID(idpelicula).body()?.genero
    _poster.value = repository3.getPeliculasID(idpelicula).body()?.poster

    } catch (e: Exception) {

    }
    }
    }



    /**      @SuppressLint("SuspiciousIndentation")
    fun getPeliculaIdDao(idpelicula: String) {

    viewModelScope.launch {

    try {
    _pelisiddao.value = repository3.getPeliculasIDDao(idpelicula).body()?.id
    _pelistitulodao.value = repository3.getPeliculasIDDao(idpelicula).body()?.titulo
    _pelisdescripciondao.value = repository3.getPeliculasIDDao(idpelicula).body()?.descipcion
    _peliscalificaciondao.value = repository3.getPeliculasIDDao(idpelicula).body()?.porcenjatevotos
    _pelislenguajedao.value = repository3.getPeliculasIDDao(idpelicula).body()?.lenguaje
    _pelisfechalanzamientodao.value = repository3.getPeliculasIDDao(idpelicula).body()?.fechalanzamiento
    _posterdao.value = repository3.getPeliculasIDDao(idpelicula).body()?.poster

    } catch (e: Exception) {
    }
    }
    }*/

    fun getListaPeliId(){

    viewModelScope.launch {

    try {
    repository3.getListaPelisId().collect{ data->

    _listapeliculasid.value = data

    }

    }catch (e: Exception){}

    }


    }

     */

    /** The mutable State that stores the status of the most recent request */
    //var moviesUiState: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
    //    private set
    init {

        viewModelScope.launch {
            try {
                fetchMoviesPopular()
                fetchMoviesNow()
                fetchMoviesTop()
                fetchMoviesUpcoming()

                getListaPeliPopularId()
                getListaPeliNowId()
                getListaPeliTopId()
                getListaPeliUpcomingId()

            } catch (e: Exception) {
            }
        }

    }

    private val _moviesListPopular = MutableStateFlow(MoviesListPopular())
    private val _moviesListNow = MutableStateFlow(MoviesListNow())
    private val _moviesListTop = MutableStateFlow(MoviesListTop())
    private val _moviesListUpcoming = MutableStateFlow(MoviesListUpcoming())
    private val _moviesListPopularId = MutableStateFlow(MoviesListPopularId())
    private val _moviesListNowId = MutableStateFlow(MoviesListNowId())
    private val _moviesListTopId = MutableStateFlow(MoviesListTopId())
    private val _moviesListUpcomingId = MutableStateFlow(MoviesListUpcomingId())

    val moviesListPopular: StateFlow<MoviesListPopular> = _moviesListPopular.asStateFlow()
    val moviesListNow: StateFlow<MoviesListNow> = _moviesListNow.asStateFlow()
    val moviesListTop: StateFlow<MoviesListTop> = _moviesListTop.asStateFlow()
    val moviesListUpcoming: StateFlow<MoviesListUpcoming> = _moviesListUpcoming.asStateFlow()
    val moviesListPopularId: StateFlow<MoviesListPopularId> = _moviesListPopularId.asStateFlow()
    val moviesListNowId: StateFlow<MoviesListNowId> = _moviesListNowId.asStateFlow()
    val moviesListTopId: StateFlow<MoviesListTopId> = _moviesListTopId.asStateFlow()
    val moviesListUpcomingId: StateFlow<MoviesListUpcomingId> = _moviesListUpcomingId.asStateFlow()






    fun fetchMoviesPopular() {
        viewModelScope.launch {

            try {
                val moviesList: List<PeliculasPopular> = repository3.getPeliculasPopular().body()!!.results

                // On success, update the state with the movies and set isLoading to false
                _moviesListPopular.value = MoviesListPopular(movies = moviesList, isLoading = false)


            } catch (e: IOException) {
            }
        }}

    fun fetchMoviesNow() {
        viewModelScope.launch {

            try {
                val moviesList: List<PeliculasNow> = repository3.getPeliculasNow().body()!!.results

                // On success, update the state with the movies and set isLoading to false
                _moviesListNow.value = MoviesListNow(movies = moviesList, isLoading = false)


            } catch (e: IOException) {
            }
        }}

    fun fetchMoviesTop() {
        viewModelScope.launch {

            try {
                val moviesList: List<PeliculasTop> = repository3.getPeliculasTop().body()!!.results

                // On success, update the state with the movies and set isLoading to false
                _moviesListTop.value = MoviesListTop(movies = moviesList, isLoading = false)


            } catch (e: IOException) {
            }
        }}

    fun fetchMoviesUpcoming() {
        viewModelScope.launch {

            try {
                val moviesList: List<PeliculasUpcoming> = repository3.getPeliculasUpcoming().body()!!.results

                // On success, update the state with the movies and set isLoading to false
                _moviesListUpcoming.value = MoviesListUpcoming(movies = moviesList, isLoading = false)


            } catch (e: IOException) {
            }
        }}

    fun getListaPeliPopularId() {

        viewModelScope.launch {
            try {
                repository3.getListaPelisPopularId().collect { data ->
                    _moviesListPopularId.value = MoviesListPopularId(movies = data.toList())
                }

            } catch (e: Exception) {
            }

        }
    }

    fun getListaPeliNowId() {

        viewModelScope.launch {
            try {
                repository3.getListaPelisNowId().collect { data ->
                    _moviesListNowId.value = MoviesListNowId(movies = data.toList())
                }

            } catch (e: Exception) {
            }

        }
    }

    fun getListaPeliTopId() {

        viewModelScope.launch {
            try {
                repository3.getListaPelisTopId().collect { data ->
                    _moviesListTopId.value = MoviesListTopId(movies = data.toList())
                }

            } catch (e: Exception) {
            }

        }
    }

    fun getListaPeliUpcomingId() {

        viewModelScope.launch {
            try {
                repository3.getListaPelisUpcomingId().collect { data ->
                    _moviesListUpcomingId.value = MoviesListUpcomingId(movies = data.toList())
                }

            } catch (e: Exception) {
            }

        }
    }


    var moviesUiStatePopularId: MoviesUiStatePopularId by mutableStateOf(MoviesUiStatePopularId.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        //getMovies()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    /**fun getMovies() {
    viewModelScope.launch {
    moviesUiState = try {
    MoviesUiState.Success(repository3.getPeliculas().body()!!.results)
    } catch (e: IOException) {
    MoviesUiState.Error
    }
    }
    }*/
    /**
    fun getMoviesId(idpelicula: String) {
    viewModelScope.launch {
    moviesUiStatePopularId = try {
    MoviesUiStatePopularId.Success(
    descipcion = repository3.getPeliculasPopularID(idpelicula).body()!!.descipcion,
    fechalanzamiento = repository3.getPeliculasPopularID(idpelicula).body()!!.fechalanzamiento,
    porcenjatevotos = repository3.getPeliculasPopularID(idpelicula).body()!!.porcenjatevotos,
    lenguaje = repository3.getPeliculasPopularID(idpelicula).body()!!.lenguaje,
    genero = repository3.getPeliculasPopularID(idpelicula).body()!!.genero,
    poster = repository3.getPeliculasPopularID(idpelicula).body()!!.poster,
    id = repository3.getPeliculasPopularID(idpelicula).body()!!.id,
    titulo = repository3.getPeliculasPopularID(idpelicula).body()!!.titulo
    )
    } catch (e: IOException) {
    MoviesUiStatePopularId.Error
    }
    }
    }*/

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val repository3 = application.container.peliculaRepository
                PeliculasPopularesViewModel(
                    repository3 = repository3,
                )
            }
        }
    }
}













