package com.example.peliculaspopulares.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.listasdao.PeliculasNowDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasPopularDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasTopDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasUpcomingDAO
import com.example.peliculaspopulares.data.listasdaoid.PeliculasNowDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasPopularDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasTopDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasUpcomingDAOID
import com.example.peliculaspopulares.model.MoviesListNow
import com.example.peliculaspopulares.model.MoviesListNowId
import com.example.peliculaspopulares.model.MoviesListPopular
import com.example.peliculaspopulares.model.MoviesListPopularId
import com.example.peliculaspopulares.model.MoviesListTop
import com.example.peliculaspopulares.model.MoviesListTopId
import com.example.peliculaspopulares.model.MoviesListUpcoming
import com.example.peliculaspopulares.model.MoviesListUpcomingId
import com.example.peliculaspopulares.model.MoviesNowUiStateDao
import com.example.peliculaspopulares.model.MoviesPopularUiStateDao
import com.example.peliculaspopulares.model.MoviesTopUiStateDao
import com.example.peliculaspopulares.model.MoviesUpcomingUiStateDao
import com.example.peliculaspopulares.model.PeliculaDaoViewModel
import com.example.peliculaspopulares.model.UserPreferencesViewModel
import com.example.peliculaspopulares.repositorio.UserPreferences
import com.example.peliculaspopulares.repositorio.UserPreferencesRepository
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine


@Composable
fun MoviesCategoriesScreen(
    moviesListPopular: MoviesListPopular,
    moviesListNow: MoviesListNow,
    moviesListTop: MoviesListTop,
    moviesListUpcoming: MoviesListUpcoming,
    moviesListPopularId: MoviesListPopularId,
    moviesListNowId: MoviesListNowId,
    moviesListTopId: MoviesListTopId,
    moviesListUpcomingId: MoviesListUpcomingId,
    moviesPopularUiState: MoviesPopularUiStateDao,
    moviesNowUiState: MoviesNowUiStateDao,
    moviesTopUiState: MoviesTopUiStateDao,
    moviesUpcomingUiState: MoviesUpcomingUiStateDao,
    peliculaViewModelDao: PeliculaDaoViewModel = viewModel(factory = PeliculaDaoViewModel.Factory),
    dataStore : UserPreferencesViewModel,
    onMovieClick: (String) -> Unit
) {

    BackHandler(enabled = false) { }

// Otros LaunchedEffect para las otras operaciones
    LaunchedEffect(moviesListPopular.movies) {
        if (moviesListPopular.movies.isNotEmpty()) {
            peliculaViewModelDao.deletePopular()
            peliculaViewModelDao.refreshMoviesPopularData(moviesListPopular.movies.map {
                PeliculasPopularDAO(it.id, it.titulo, it.posterpath, it.backdrop)
            })
        }
    }

    // Otros LaunchedEffect para las otras operaciones
    LaunchedEffect(moviesListNow.movies) {
        if (moviesListNow.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteNow()
            peliculaViewModelDao.refreshMoviesNowData(moviesListNow.movies.map {
                PeliculasNowDAO(it.id, it.titulo, it.posterpath, it.backdrop)
            })
        }
    }

    // Otros LaunchedEffect para las otras operaciones
    LaunchedEffect(moviesListTop.movies) {
        if (moviesListTop.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteTop()
            peliculaViewModelDao.refreshMoviesTopData(moviesListTop.movies.map {
                PeliculasTopDAO(it.id, it.titulo, it.posterpath, it.backdrop)
            })
        }
    }

    // Otros LaunchedEffect para las otras operaciones
    LaunchedEffect(moviesListUpcoming.movies) {
        if (moviesListUpcoming.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteUpcoming()
            peliculaViewModelDao.refreshMoviesUpcomingData(moviesListUpcoming.movies.map {
                PeliculasUpcomingDAO(it.id, it.titulo, it.posterpath, it.backdrop)
            })
        }
    }


// SOLUCIÓN PARA EL CÓDIGO SELECCIONADO
// Este bloque se ejecutará cada vez que la lista moviesUiStateIdLista.movies cambie.
    LaunchedEffect(moviesListPopularId.movies) {
        // Solo ejecutamos si la lista no está vacía para evitar trabajo innecesario
        if (moviesListPopularId.movies.isNotEmpty()) {
            peliculaViewModelDao.deletePopularId()
            peliculaViewModelDao.refreshMoviesPopularDataId(moviesListPopularId.movies.map {
                PeliculasPopularDAOID(
                    it.id,
                    it.titulo,
                    it.descipcion,
                    it.porcenjatevotos,
                    it.lenguaje,
                    it.fechalanzamiento,
                    it.poster
                )
            })
        }
    }

    // SOLUCIÓN PARA EL CÓDIGO SELECCIONADO
// Este bloque se ejecutará cada vez que la lista moviesUiStateIdLista.movies cambie.
    LaunchedEffect(moviesListNowId.movies) {
        // Solo ejecutamos si la lista no está vacía para evitar trabajo innecesario
        if (moviesListNowId.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteNowId()
            peliculaViewModelDao.refreshMoviesNowDataId(moviesListNowId.movies.map {
                PeliculasNowDAOID(
                    it.id,
                    it.titulo,
                    it.descipcion,
                    it.porcenjatevotos,
                    it.lenguaje,
                    it.fechalanzamiento,
                    it.poster
                )
            })
        }
    }

    // SOLUCIÓN PARA EL CÓDIGO SELECCIONADO
// Este bloque se ejecutará cada vez que la lista moviesUiStateIdLista.movies cambie.
    LaunchedEffect(moviesListTopId.movies) {
        // Solo ejecutamos si la lista no está vacía para evitar trabajo innecesario
        if (moviesListTopId.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteTopId()
            peliculaViewModelDao.refreshMoviesTopDataId(moviesListTopId.movies.map {
                PeliculasTopDAOID(
                    it.id,
                    it.titulo,
                    it.descipcion,
                    it.porcenjatevotos,
                    it.lenguaje,
                    it.fechalanzamiento,
                    it.poster
                )
            })
        }
    }

    // SOLUCIÓN PARA EL CÓDIGO SELECCIONADO
// Este bloque se ejecutará cada vez que la lista moviesUiStateIdLista.movies cambie.
    LaunchedEffect(moviesListUpcomingId.movies) {
        // Solo ejecutamos si la lista no está vacía para evitar trabajo innecesario
        if (moviesListUpcomingId.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteUpcomingId()
            peliculaViewModelDao.refreshMoviesUpcomingDataId(moviesListUpcomingId.movies.map {
                PeliculasUpcomingDAOID(
                    it.id,
                    it.titulo,
                    it.descipcion,
                    it.porcenjatevotos,
                    it.lenguaje,
                    it.fechalanzamiento,
                    it.poster
                )
            })
        }
    }

    PhotosCategoriesGridScreen(
        moviesPopularUiState = moviesPopularUiState,
        moviesNowUiState = moviesNowUiState,
        moviesTopUiState = moviesTopUiState,
        moviesUpcomingUiState = moviesUpcomingUiState,
        dataStore = dataStore,
        onMovieClick = { onMovieClick(it) }
    )



}
@Composable
fun CategoriesPhotoCard(moviesPopularUiState: MoviesPopularUiStateDao,
                        moviesNowUiState: MoviesNowUiStateDao,
                        moviesTopUiState: MoviesTopUiStateDao,
                        moviesUpcomingUiState: MoviesUpcomingUiStateDao,
                        dataStore: UserPreferencesViewModel,
                        onMovieClick: (String) -> Unit){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(
            onClick = {
                scope.launch {
                    logOut(context, dataStore)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Logout")

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "pupular",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))
        PopularScreen(
            moviesPopularUiState = moviesPopularUiState,
            modifier = Modifier.fillMaxSize(),
            onMovieClick = { onMovieClick(it) },
            retryAction = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "new",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        NowScreen(
            moviesNowUiState = moviesNowUiState,
            modifier = Modifier.fillMaxSize(),
            retryAction = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "top",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        TopScreen(
            moviesTopUiState = moviesTopUiState,
            modifier = Modifier.fillMaxSize(),
            retryAction = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "upcoming",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        UpcomingScreen(
            moviesUpcomingUiState = moviesUpcomingUiState,
            modifier = Modifier.fillMaxSize(),
            retryAction = {}
        )

    }
}





@Composable
fun PhotosCategoriesGridScreen(moviesPopularUiState: MoviesPopularUiStateDao,
                               moviesNowUiState: MoviesNowUiStateDao,
                               moviesTopUiState: MoviesTopUiStateDao,
                               moviesUpcomingUiState: MoviesUpcomingUiStateDao,
                               onMovieClick: (String) -> Unit,
                               dataStore: UserPreferencesViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CategoriesPhotoCard(
            moviesPopularUiState = moviesPopularUiState,
            moviesNowUiState = moviesNowUiState,
            moviesTopUiState = moviesTopUiState,
            moviesUpcomingUiState = moviesUpcomingUiState,
            dataStore = dataStore,
            onMovieClick = { onMovieClick(it) }
        )
    }
}

suspend fun logOut(context: Context, dataStore: UserPreferencesViewModel) {

    val credentialManager = CredentialManager.create(context)
    val request = ClearCredentialStateRequest()

    try {
        credentialManager.clearCredentialState(request)
        dataStore.logout()
        FirebaseAuth.getInstance().signOut()
        Log.d("Logout", "Credential Manager state cleared successfully")
    } catch (e: Exception) {
        Log.e("Logout", "Error clearing credential state", e)
    }
    Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CategoriesPhotosGridScreenPreview() {
    PeliculasPopularesTheme {
        val mockData = List(10) { PeliculasPopularDAO("$it", "", "", "") }
        val mockData1 = List(10) { PeliculasNowDAO("$it", "", "", "") }
        val mockData2 = List(10) { PeliculasTopDAO("$it", "", "", "") }
        val mockData3 = List(10) { PeliculasUpcomingDAO("$it", "", "", "") }
        val userPreferencesRepository = UserPreferencesRepository(UserPreferences(LocalContext.current))
        PhotosCategoriesGridScreen(
            onMovieClick = { },
            moviesPopularUiState = MoviesPopularUiStateDao.Success(mockData),
            moviesNowUiState = MoviesNowUiStateDao.Success(mockData1),
            moviesTopUiState = MoviesTopUiStateDao.Success(mockData2),
            moviesUpcomingUiState = MoviesUpcomingUiStateDao.Success(mockData3),
            dataStore = UserPreferencesViewModel(userPreferencesRepository)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun CategoriesResultScreenPreview() {
    PeliculasPopularesTheme {
        NowResultScreen(stringResource(R.string.placeholder_result))
    }
}