package com.example.peliculaspopulares.repositorio.network


import com.example.peliculaspopulares.data.listas.PeliculasNow
import com.example.peliculaspopulares.data.listas.PeliculasPopular
import com.example.peliculaspopulares.data.listas.PeliculasTop
import com.example.peliculaspopulares.data.listas.PeliculasUpcoming
import com.example.peliculaspopulares.data.listasid.PeliculaNowID
import com.example.peliculaspopulares.data.listasid.PeliculaPopularID
import com.example.peliculaspopulares.data.listasid.PeliculaTopID
import com.example.peliculaspopulares.data.listasid.PeliculaUpcomingID
import com.example.peliculaspopulares.data.paginas.PaginaNow
import com.example.peliculaspopulares.data.paginas.PaginaPopular
import com.example.peliculaspopulares.data.paginas.PaginaTop
import com.example.peliculaspopulares.data.paginas.PaginaUpcoming
import com.example.peliculaspopulares.service.MoshiPeliculaInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


interface PeliculaRepository {

    suspend fun getPeliculasPopular(): Response<PaginaPopular>

    suspend fun getPeliculasTop(): Response<PaginaTop>

    suspend fun getPeliculasNow(): Response<PaginaNow>

    suspend fun getPeliculasUpcoming(): Response<PaginaUpcoming>

    suspend fun getPeliculasPopularID (id: String): Response<PeliculaPopularID>

    suspend fun getPeliculasNowID (id: String): Response<PeliculaNowID>

    suspend fun getPeliculasTopID (id: String): Response<PeliculaTopID>

    suspend fun getPeliculasUpcomingID (id: String): Response<PeliculaUpcomingID>

    suspend fun getListaPelisPopularId() : Flow<List<PeliculaPopularID>>

    suspend fun getListaPelisNowId() : Flow<List<PeliculaNowID>>

    suspend fun getListaPelisTopId() : Flow<List<PeliculaTopID>>

    suspend fun getListaPelisUpcomingId() : Flow<List<PeliculaUpcomingID>>

}

class NetworkPeliculaRepository(private val moshiPeliculaInterface: MoshiPeliculaInterface, private val refreshIntervalMs: Long = 10) :
    PeliculaRepository {

    override suspend fun getPeliculasPopular(): Response<PaginaPopular> = moshiPeliculaInterface.getPeliculasPopular()

    override suspend fun getPeliculasTop(): Response<PaginaTop> = moshiPeliculaInterface.getPeliculasTop()

    override suspend fun getPeliculasNow(): Response<PaginaNow> = moshiPeliculaInterface.getPeliculasNow()

    override suspend fun getPeliculasUpcoming(): Response<PaginaUpcoming> = moshiPeliculaInterface.getPeliculasUpcoming()

    override suspend fun getPeliculasPopularID(id: String): Response<PeliculaPopularID> = moshiPeliculaInterface.getPeliculasPopularID(id)

    override suspend fun getPeliculasNowID(id: String): Response<PeliculaNowID> = moshiPeliculaInterface.getPeliculasNowID(id)

    override suspend fun getPeliculasTopID(id: String): Response<PeliculaTopID> = moshiPeliculaInterface.getPeliculasTopID(id)

    override suspend fun getPeliculasUpcomingID(id: String): Response<PeliculaUpcomingID> = moshiPeliculaInterface.getPeliculasUpcomingID(id)

    override suspend fun getListaPelisPopularId(): Flow<List<PeliculaPopularID>> = flow{

        val listaPelis : List<PeliculasPopular>? = moshiPeliculaInterface.getPeliculasPopular().body()?.results
        val listaPelisId = mutableListOf<PeliculaPopularID>()


        if (listaPelis != null) {
            for(i in listaPelis){
                val peliId = moshiPeliculaInterface.getPeliculasPopularID(i.id).body()
                if (peliId != null) {
                    listaPelisId.add(peliId)
                }
                emit(listaPelisId)
                delay(refreshIntervalMs)
            }
        }
    }

    override suspend fun getListaPelisNowId(): Flow<List<PeliculaNowID>> = flow{

        val listaPelis : List<PeliculasNow>? = moshiPeliculaInterface.getPeliculasNow().body()?.results
        val listaPelisId = mutableListOf<PeliculaNowID>()


        if (listaPelis != null) {
            for(i in listaPelis){
                val peliId = moshiPeliculaInterface.getPeliculasNowID(i.id).body()
                if (peliId != null) {
                    listaPelisId.add(peliId)
                }
                emit(listaPelisId)
                delay(refreshIntervalMs)
            }
        }
    }

    override suspend fun getListaPelisTopId(): Flow<List<PeliculaTopID>> = flow{

        val listaPelis : List<PeliculasTop>? = moshiPeliculaInterface.getPeliculasTop().body()?.results
        val listaPelisId = mutableListOf<PeliculaTopID>()


        if (listaPelis != null) {
            for(i in listaPelis){
                val peliId = moshiPeliculaInterface.getPeliculasTopID(i.id).body()
                if (peliId != null) {
                    listaPelisId.add(peliId)
                }
                emit(listaPelisId)
                delay(refreshIntervalMs)
            }
        }
    }

    override suspend fun getListaPelisUpcomingId(): Flow<List<PeliculaUpcomingID>> = flow{

        val listaPelis : List<PeliculasUpcoming>? = moshiPeliculaInterface.getPeliculasUpcoming().body()?.results
        val listaPelisId = mutableListOf<PeliculaUpcomingID>()


        if (listaPelis != null) {
            for(i in listaPelis){
                val peliId = moshiPeliculaInterface.getPeliculasUpcomingID(i.id).body()
                if (peliId != null) {
                    listaPelisId.add(peliId)
                }
                emit(listaPelisId)
                delay(refreshIntervalMs)
            }
        }
    }

}