package com.example.peliculaspopulares.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.listasdaoid.PeliculasPopularDAOID
import com.example.peliculaspopulares.model.MoviesPopularUiStateIdDao
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme


@Composable
fun DetailsScreen(
    moviesUiStateId: MoviesPopularUiStateIdDao,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier
) {

    when (moviesUiStateId) {
        is MoviesPopularUiStateIdDao.Loading -> LoadingScreenId()
        is MoviesPopularUiStateIdDao.Success -> PhotosPagerScreenId(photos = PeliculasPopularDAOID(
            id = moviesUiStateId.id,
            titulo = moviesUiStateId.titulo,
            poster = moviesUiStateId.poster,
            descipcion = moviesUiStateId.descipcion,
            porcenjatevotos = moviesUiStateId.porcenjatevotos,
            lenguaje = moviesUiStateId.lenguaje,
            //genero = moviesUiStatePopularId.genero,
            fechalanzamiento = moviesUiStateId.fechalanzamiento
        ), modifier = modifier)
        else -> ErrorScreenId(retryAction)
    }
}

@Composable
fun ResultScreenId(photos: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = photos)
    }
}

@Composable
fun LoadingScreenId() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreenId(retryAction: () -> Unit) {
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
fun MoviesDetailsCard(photo: PeliculasPopularDAOID) {

    Card(modifier = Modifier.height(250.dp).
    padding(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = CardDefaults.shape){
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.BASE_URL_IMAGEN + photo.poster)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.movies_photo),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun PhotosPagerScreenId(
    photos: PeliculasPopularDAOID,
    modifier: Modifier
    //contentPadding: PaddingValues = PaddingValues(16.dp),
) {

    Card(
        modifier = modifier.verticalScroll(rememberScrollState()),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = CardDefaults.shape,
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MoviesDetailsCard(photo = photos)
            photos.titulo?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(5.dp).fillMaxSize().wrapContentSize(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            photos.descipcion?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Column(modifier = modifier.fillMaxWidth()) {
                Row(modifier = modifier.fillMaxSize()) {
                    Text(
                        text = "Lenguaje: ",
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    photos.lenguaje?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(5.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                Row(modifier = modifier.fillMaxSize()) {
                    Text(
                        text = "Fecha de lanzamiento: ",
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    photos.fechalanzamiento?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(5.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
                Row(modifier = modifier.fillMaxSize()) {
                    Text(
                        text = "Porcentaje de Votos: ",
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = photos.porcenjatevotos.toString(),
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                /** Row(modifier = modifier.fillMaxSize()) {
                Text(
                text = "Genero: ",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.titleLarge
                )
                for (genero in photos.genero) {
                Text(
                text = genero.name,
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.titleLarge
                )
                }
                }*/
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun PhotosPagerScreenIdPreview() {
    PeliculasPopularesTheme {
        val mockData = PeliculasPopularDAOID("9", "","", 11F,",",
            ",", "")
        PhotosPagerScreenId(photos = mockData, modifier = Modifier)
    }
}



@Preview(showBackground = true)
@Composable
fun ResultScreenIdPreview() {
    PeliculasPopularesTheme {
        ResultScreenId(stringResource(R.string.placeholder_result))
    }
}