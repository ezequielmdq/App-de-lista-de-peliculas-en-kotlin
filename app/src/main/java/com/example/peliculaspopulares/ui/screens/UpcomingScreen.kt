package com.example.peliculaspopulares.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.listasdao.PeliculasUpcomingDAO
import com.example.peliculaspopulares.model.MoviesUpcomingUiStateDao
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme

@Composable
fun UpcomingScreen(moviesUpcomingUiState: MoviesUpcomingUiStateDao,
              retryAction: () -> Unit,
              modifier: Modifier,
              /**onMovieClick: (String) -> Unit*/) {

    when (moviesUpcomingUiState) {
        is MoviesUpcomingUiStateDao.Loading -> UpcomingLoadingScreen()
        is MoviesUpcomingUiStateDao.Success -> {
            PhotosUpcomingGridScreen(
                photos = moviesUpcomingUiState.photos,
                /**onMovieClick = {onMovieClick(it)},*/
                modifier = modifier
            )
        }
        else -> UpcomingErrorScreen(retryAction)
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */

@Composable
fun UpcomingLoadingScreen() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun UpcomingErrorScreen(retryAction: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun UpcomingPhotoCard(photo: PeliculasUpcomingDAO,
    //onMovieClick: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = photo.titulo ?: "",
            style = TextStyle(
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(16.dp).wrapContentSize(),
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .height(500.dp)
                .fillMaxSize(),
            //onClick = { onMovieClick(photo.id) },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = CardDefaults.shape
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(BuildConfig.BASE_URL_IMAGEN + photo.posterpath)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.movies_photo),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(),

                    )
            }
        }
    }
}

@Composable
fun PhotosUpcomingGridScreen(
    modifier: Modifier,
    photos: List<PeliculasUpcomingDAO>,
    //onMovieClick: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(16.dp),
) {

    val state = rememberPagerState { photos.size }

    HorizontalPager(state = state,
        modifier = modifier
            .wrapContentHeight()
            .fillMaxSize(),
        contentPadding = contentPadding,
        pageSpacing = 20.dp
    ) { page ->

        UpcomingPhotoCard(photos[page],
            //onMovieClick = onMovieClick


        )

    }
}

@Composable
fun UpcomingResultScreen(photos: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = photos)
    }
}

/**@Preview(showBackground = true)
@Composable
fun NewPhotosGridScreenPreview() {
PeliculasPopularesTheme {
val mockData = List(10) { PeliculasPopularDAO("$it", "", "", "") }
PhotosNewGridScreen(
photos = mockData,
//onMovieClick = { },
modifier = Modifier
)
}
}*/



@Preview(showBackground = true)
@Composable
fun UpcomingResultScreenPreview() {
    PeliculasPopularesTheme {
        NowResultScreen(stringResource(R.string.placeholder_result))
    }
}