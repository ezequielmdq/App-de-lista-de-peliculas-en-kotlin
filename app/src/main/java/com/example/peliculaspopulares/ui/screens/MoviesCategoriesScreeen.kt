package com.example.peliculaspopulares.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.peliculaspopulares.model.MoviesNowDao
import com.example.peliculaspopulares.model.MoviesNowUiStateDao
import com.example.peliculaspopulares.model.MoviesPopularDao
import com.example.peliculaspopulares.model.MoviesPopularUiStateDao
import com.example.peliculaspopulares.model.MoviesTopDao
import com.example.peliculaspopulares.model.MoviesTopUiStateDao
import com.example.peliculaspopulares.model.MoviesUpcomingDao
import com.example.peliculaspopulares.model.MoviesUpcomingUiStateDao
import com.example.peliculaspopulares.model.PeliculaDaoViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme


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
    moviesPopularUiStateDao: MoviesPopularDao,
    moviesNowUiStateDao: MoviesNowDao,
    moviesTopUiStateDao: MoviesTopDao,
    moviesUpcomingUiStateDao: MoviesUpcomingDao,
    //onMovieClick: (String) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    peliculaViewModel: PeliculasPopularesViewModel = viewModel(factory = PeliculasPopularesViewModel.Factory),
    peliculaViewModelDao: PeliculaDaoViewModel = viewModel(factory = PeliculaDaoViewModel.Factory)) {


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
        retryAction = retryAction,
        modifier = modifier,
        contentPadding = contentPadding
    )



}
@Composable
fun CategoriesPhotoCard(moviesPopularUiState: MoviesPopularUiStateDao,
                        moviesNowUiState: MoviesNowUiStateDao,
                        moviesTopUiState: MoviesTopUiStateDao,
                        moviesUpcomingUiState: MoviesUpcomingUiStateDao,
                        modifier: Modifier, retryAction: () -> Unit, /**, onMovieClick: (String) -> Unit*/){

    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "pupular",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp).wrapContentSize(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))
        PopularScreen(
            moviesPopularUiState = moviesPopularUiState,
            retryAction = retryAction,
            modifier = modifier,
            /**onMovieClick = onMovieClick*/
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "now",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp).wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        NowScreen(
            moviesNowUiState = moviesNowUiState,
            retryAction = retryAction,
            modifier = modifier,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "top",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp).wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        TopScreen(
            moviesTopUiState = moviesTopUiState,
            retryAction = retryAction,
            modifier = modifier,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "upcoming",
            style = TextStyle(
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(8.dp).wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        UpcomingScreen(
            moviesUpcomingUiState = moviesUpcomingUiState,
            retryAction = retryAction,
            modifier = modifier,
        )

    }
    }



@Composable
fun PhotosCategoriesGridScreen(moviesPopularUiState: MoviesPopularUiStateDao,
                               moviesNowUiState: MoviesNowUiStateDao,
                               moviesTopUiState: MoviesTopUiStateDao,
                               moviesUpcomingUiState: MoviesUpcomingUiStateDao,
                               retryAction: () -> Unit,
                               modifier: Modifier = Modifier,
                               contentPadding: PaddingValues = PaddingValues(16.dp),
                               /**onMovieClick: (String) -> Unit*/) {

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ) {
        CategoriesPhotoCard(
            moviesPopularUiState = moviesPopularUiState,
            moviesNowUiState = moviesNowUiState,
            moviesTopUiState = moviesTopUiState,
            moviesUpcomingUiState = moviesUpcomingUiState,
            modifier = modifier,
            retryAction = retryAction,
            /**onMovieClick = onMovieClick*/
            )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesPhotosGridScreenPreview() {
    PeliculasPopularesTheme {
        val mockData = List(10) { PeliculasPopularDAO("$it", "", "", "") }
        val mockData1 = List(10) { PeliculasNowDAO("$it", "", "", "") }
        val mockData2 = List(10) { PeliculasTopDAO("$it", "", "", "") }
        val mockData3 = List(10) { PeliculasUpcomingDAO("$it", "", "", "") }
        PhotosCategoriesGridScreen(
            //onMovieClick = { },
            moviesPopularUiState = MoviesPopularUiStateDao.Success(mockData),
            retryAction = { },
            modifier = Modifier,
            moviesNowUiState = MoviesNowUiStateDao.Success(mockData1),
            moviesTopUiState = MoviesTopUiStateDao.Success(mockData2),
            moviesUpcomingUiState = MoviesUpcomingUiStateDao.Success(mockData3)
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