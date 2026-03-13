package com.example.peliculaspopulares.repositorio.local


import androidx.annotation.WorkerThread
import com.example.peliculaspopulares.data.listasdao.PeliculasNowDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasPopularDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasTopDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasUpcomingDAO
import com.example.peliculaspopulares.data.listasdaoid.PeliculasNowDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasPopularDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasTopDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasUpcomingDAOID
import com.example.peliculaspopulares.service.PeliculaDao
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    // Gets the movies from the local database
    fun getMoviesPopularStream(): Flow<List<PeliculasPopularDAO>>

    fun getMoviesNowStream(): Flow<List<PeliculasNowDAO>>

    fun getMoviesTopStream(): Flow<List<PeliculasTopDAO>>

    fun getMoviesUpcomingStream(): Flow<List<PeliculasUpcomingDAO>>

    fun getMoviesPopularStreamId(data: String): Flow<PeliculasPopularDAOID>

    fun getMoviesNowStreamId(data: String): Flow<PeliculasNowDAOID>

    fun getMoviesTopStreamId(data: String): Flow<PeliculasTopDAOID>

    fun getMoviesUpcomingStreamId(data: String): Flow<PeliculasUpcomingDAOID>


    // Fetches movies from the network and saves them to the database
    suspend fun refreshMoviesPopular(peliculas: List<PeliculasPopularDAO>)

    suspend fun refreshMoviesNow(peliculas: List<PeliculasNowDAO>)

    suspend fun refreshMoviesTop(peliculas: List<PeliculasTopDAO>)

    suspend fun refreshMoviesUpcoming(peliculas: List<PeliculasUpcomingDAO>)

    suspend fun refreshMoviesPopularId(peliculas: List<PeliculasPopularDAOID>)

    suspend fun refreshMoviesNowId(peliculas: List<PeliculasNowDAOID>)

    suspend fun refreshMoviesTopId(peliculas: List<PeliculasTopDAOID>)

    suspend fun refreshMoviesUpcomingId(peliculas: List<PeliculasUpcomingDAOID>)
}

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PeliculaRepositoryDao(private val peliculaDao : PeliculaDao) : MoviesRepository {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPeliculas: Flow<List<PeliculasPopularDAO>> = peliculaDao.getPeliculasPopular()

    val datoDetalle: (String) -> Flow<PeliculasPopularDAOID> = fun(data: String): Flow<PeliculasPopularDAOID> {
        return peliculaDao.getDetallePopular(data)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(peliculas: List<PeliculasPopularDAO>) = peliculaDao.insertPopular(peliculas)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun intertPeli(pelicula: List<PeliculasPopularDAOID>) = peliculaDao.insertPopularID(pelicula)


    suspend fun deletePopular() = peliculaDao.deleteAllPopular()

    suspend fun deleteNow() = peliculaDao.deleteAllNow()

    suspend fun deleteTop() = peliculaDao.deleteAllTop()

    suspend fun deleteUpcoming() = peliculaDao.deleteAllUpcoming()

    suspend fun deletePopularId() = peliculaDao.deleteAllPopularId()

    suspend fun deleteNowId() = peliculaDao.deleteAllNowId()

    suspend fun deleteTopId() = peliculaDao.deleteAllTopId()

    suspend fun deleteUpcomingId() = peliculaDao.deleteAllUpcomingId()

    override fun getMoviesPopularStream(): Flow<List<PeliculasPopularDAO>> {
        return peliculaDao.getPeliculasPopular()
    }

    override fun getMoviesNowStream(): Flow<List<PeliculasNowDAO>> {
        return peliculaDao.getPeliculasNow()
    }

    override fun getMoviesTopStream(): Flow<List<PeliculasTopDAO>> {
        return peliculaDao.getPeliculasTop()
    }

    override fun getMoviesUpcomingStream(): Flow<List<PeliculasUpcomingDAO>> {
        return peliculaDao.getPeliculasUpcoming()
    }

    override fun getMoviesPopularStreamId(data: String): Flow<PeliculasPopularDAOID> {
        return peliculaDao.getDetallePopular(data)
    }

    override fun getMoviesNowStreamId(data: String): Flow<PeliculasNowDAOID> {
        return peliculaDao.getDetalleNow(data)
    }

    override fun getMoviesTopStreamId(data: String): Flow<PeliculasTopDAOID> {
        return peliculaDao.getDetalleTop(data)
    }

    override fun getMoviesUpcomingStreamId(data: String): Flow<PeliculasUpcomingDAOID> {
        return peliculaDao.getDetalleUpcoming(data)
    }

    override suspend fun refreshMoviesPopular(peliculas: List<PeliculasPopularDAO>) {
        try {
            peliculaDao.insertPopular(peliculas)
        } catch (e: Exception) {

        }
    }

    override suspend fun refreshMoviesNow(peliculas: List<PeliculasNowDAO>) {
        try {
            peliculaDao.insertNow(peliculas)
        } catch (e: Exception) {

        }
    }

    override suspend fun refreshMoviesTop(peliculas: List<PeliculasTopDAO>) {
        try {
            peliculaDao.insertTop(peliculas)
        } catch (e: Exception) {

        }
    }

    override suspend fun refreshMoviesUpcoming(peliculas: List<PeliculasUpcomingDAO>) {
        try {
            peliculaDao.insertUpcoming(peliculas)
        } catch (e: Exception) {

        }
    }


    override suspend fun refreshMoviesPopularId(peliculas: List<PeliculasPopularDAOID>) {
        try {
            peliculaDao.insertPopularID(peliculas)
        } catch (e: Exception) {
        }

    }

    override suspend fun refreshMoviesNowId(peliculas: List<PeliculasNowDAOID>) {
        try {
            peliculaDao.insertNowID(peliculas)
        } catch (e: Exception) {
        }

    }

    override suspend fun refreshMoviesTopId(peliculas: List<PeliculasTopDAOID>) {
        try {
            peliculaDao.insertTopID(peliculas)
        } catch (e: Exception) {
        }

    }

    override suspend fun refreshMoviesUpcomingId(peliculas: List<PeliculasUpcomingDAOID>) {
        try {
            peliculaDao.insertUpcomingID(peliculas)
        } catch (e: Exception) {
        }

    }
}

