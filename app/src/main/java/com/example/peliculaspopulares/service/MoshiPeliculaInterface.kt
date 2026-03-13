package com.example.peliculaspopulares.service

import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.data.listasid.PeliculaNowID
import com.example.peliculaspopulares.data.listasid.PeliculaPopularID
import com.example.peliculaspopulares.data.listasid.PeliculaTopID
import com.example.peliculaspopulares.data.listasid.PeliculaUpcomingID
import com.example.peliculaspopulares.data.paginas.PaginaNow
import com.example.peliculaspopulares.data.paginas.PaginaPopular
import com.example.peliculaspopulares.data.paginas.PaginaTop
import com.example.peliculaspopulares.data.paginas.PaginaUpcoming
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MoshiPeliculaInterface {

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_POPULAR + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE )
    suspend fun getPeliculasPopular(): Response<PaginaPopular>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_TOP + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE )
    suspend fun getPeliculasTop(): Response<PaginaTop>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_NOW + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE )
    suspend fun getPeliculasNow(): Response<PaginaNow>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_UPCOMING + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE )
    suspend fun getPeliculasUpcoming(): Response<PaginaUpcoming>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_PELICULAID + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE)
    suspend fun getPeliculasPopularID (@Path("movieid") id: String?) : Response<PeliculaPopularID>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_PELICULAID + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE)
    suspend fun getPeliculasNowID (@Path("movieid") id: String?) : Response<PeliculaNowID>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_PELICULAID + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE)
    suspend fun getPeliculasTopID (@Path("movieid") id: String?) : Response<PeliculaTopID>

    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_PELICULAID + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE)
    suspend fun getPeliculasUpcomingID (@Path("movieid") id: String?) : Response<PeliculaUpcomingID>

}

