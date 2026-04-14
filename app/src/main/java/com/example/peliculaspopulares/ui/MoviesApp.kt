package com.example.peliculaspopulares.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.model.PeliculaDaoViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.model.UserPreferencesViewModel
import com.example.peliculaspopulares.ui.login.LoginScreen
import com.example.peliculaspopulares.ui.login.RegisterScreen
import com.example.peliculaspopulares.ui.screens.DetailsScreen
import com.example.peliculaspopulares.ui.screens.MoviesCategoriesScreen
import com.example.peliculaspopulares.ui.screens.NowScreen
import com.example.peliculaspopulares.ui.screens.PopularScreen
import com.example.peliculaspopulares.ui.screens.TopScreen
import com.example.peliculaspopulares.ui.screens.UpcomingScreen


enum class MoviesScreenApp(@StringRes val title: Int) {
    Start(title = R.string.movies),
    Details(title = R.string.details),
    Popular(title = R.string.popular),
    NowPlaying(title = R.string.nowplaying),
    TopRated(title = R.string.toprated),
    Upcoming(title = R.string.upcoming),
    Login(title = R.string.login),
    Register(title = R.string.register)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesAppBar(
    currentScreen : MoviesScreenApp,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}



@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesApp(modifier: Modifier, navController: NavHostController = rememberNavController()) {

    val peliculaViewModel: PeliculasPopularesViewModel = viewModel(factory = PeliculasPopularesViewModel.Factory)

    val peliculaViewModelDao: PeliculaDaoViewModel = viewModel(factory = PeliculaDaoViewModel.Factory)

    val userPreferencesViewModel: UserPreferencesViewModel = viewModel(factory = UserPreferencesViewModel.Factory)


    val moviesListPopular by peliculaViewModel.moviesListPopular.collectAsStateWithLifecycle()
    val moviesListNow by peliculaViewModel.moviesListNow.collectAsStateWithLifecycle()
    val moviesListTop by peliculaViewModel.moviesListTop.collectAsStateWithLifecycle()
    val moviesListUpcoming by peliculaViewModel.moviesListUpcoming.collectAsStateWithLifecycle()
    val moviesListPopularId by peliculaViewModel.moviesListPopularId.collectAsStateWithLifecycle()
    val moviesListNowId by peliculaViewModel.moviesListNowId.collectAsStateWithLifecycle()
    val moviesListTopId by peliculaViewModel.moviesListTopId.collectAsStateWithLifecycle()
    val moviesListUpcomingId by peliculaViewModel.moviesListUpcomingId.collectAsStateWithLifecycle()

    val moviesPopularUiState by peliculaViewModelDao.moviesPopularUiStateDao.collectAsState()
    val moviesNewUiStateDao by peliculaViewModelDao.moviesNowUiStateDao.collectAsState()
    val moviesTopUiStateDao by peliculaViewModelDao.moviesTopUiStateDao.collectAsState()
    val moviesUpcomingUiStateDao by peliculaViewModelDao.moviesUpcomingUiStateDao.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()

    //val session by userPreferencesViewModel.sessionState.collectAsStateWithLifecycle()
    val session by userPreferencesViewModel.session.collectAsStateWithLifecycle()

    val currentScreen = MoviesScreenApp.valueOf(
        backStackEntry?.destination?.route ?: MoviesScreenApp.Login.name
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MoviesAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                modifier = Modifier.wrapContentSize()
            )
        },

        ) { innerPadding ->

        Surface(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {




            NavHost(
                navController = navController,
                startDestination = if (session.sesion)MoviesScreenApp.Start.name else MoviesScreenApp.Login.name,
                modifier = Modifier.fillMaxSize()

            ) {

                composable(route = MoviesScreenApp.Login.name) {
                    LoginScreen(
                        onUserLoginButtonClicked = {
                            navController.navigate(MoviesScreenApp.Start.name)

                        },
                        onUserRegisterButtonClicked = {
                            navController.navigate(MoviesScreenApp.Register.name)
                        },
                        sessionLogin = session,
                        dataStore = userPreferencesViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = MoviesScreenApp.Register.name) {
                    RegisterScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRegisterButtonClicked = {
                            navController.navigate(MoviesScreenApp.Login.name)
                        }
                    )
                }

                composable(route = MoviesScreenApp.Start.name) {
                    MoviesCategoriesScreen(
                        moviesListPopular = moviesListPopular,
                        moviesListNow = moviesListNow,
                        moviesListTop = moviesListTop,
                        moviesListUpcoming = moviesListUpcoming,
                        moviesListPopularId = moviesListPopularId,
                        moviesListNowId = moviesListNowId,
                        moviesListTopId = moviesListTopId,
                        moviesListUpcomingId = moviesListUpcomingId,
                        moviesPopularUiState = peliculaViewModelDao.moviesPopularUiState,
                        moviesNowUiState = peliculaViewModelDao.moviesNowUiState,
                        moviesTopUiState = peliculaViewModelDao.moviesTopUiState,
                        moviesUpcomingUiState = peliculaViewModelDao.moviesUpcomingUiState,
                        dataStore = userPreferencesViewModel,
                        onMovieClick = {
                            peliculaViewModel.getMoviesId(it)
                            peliculaViewModelDao.getMoviesId(it)
                            navController.navigate(MoviesScreenApp.Details.name)
                        }
                    )
                }

                composable(route = MoviesScreenApp.Popular.name)  {
                    PopularScreen(
                        moviesPopularUiState = peliculaViewModelDao.moviesPopularUiState,
                        retryAction = { },
                        onMovieClick = {
                            peliculaViewModel.getMoviesId(it)
                            peliculaViewModelDao.getMoviesId(it)
                            navController.navigate(MoviesScreenApp.Details.name)
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                }

                composable(route = MoviesScreenApp.NowPlaying.name) {
                    NowScreen(
                        moviesNowUiState = peliculaViewModelDao.moviesNowUiState,
                        retryAction = {  },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = MoviesScreenApp.TopRated.name) {
                    TopScreen(
                        moviesTopUiState = peliculaViewModelDao.moviesTopUiState,
                        retryAction = {  },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = MoviesScreenApp.Upcoming.name) {
                    UpcomingScreen(
                        moviesUpcomingUiState = peliculaViewModelDao.moviesUpcomingUiState,
                        retryAction = {  },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = MoviesScreenApp.Details.name) {
                    DetailsScreen(
                        moviesUiStateId = peliculaViewModelDao.moviesPopularUiStateId,
                        retryAction = {  },
                        modifier = Modifier.fillMaxSize()
                    )
                }


            }


        }
    }
}
