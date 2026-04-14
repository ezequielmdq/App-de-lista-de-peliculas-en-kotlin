package com.example.peliculaspopulares.repositorio.network

import android.content.Context
import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.repositorio.UserPreferences
import com.example.peliculaspopulares.repositorio.UserPreferencesRepository
import com.example.peliculaspopulares.repositorio.local.PeliculaRepositoryDao
import com.example.peliculaspopulares.repositorio.local.PeliculaRoomDatabase.Companion.getDatabase
import com.example.peliculaspopulares.service.MoshiPeliculaInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {

    val peliculaRepository : PeliculaRepository

    val peliculaRepositoryDao : PeliculaRepositoryDao

    val userPreferencesRepository : UserPreferencesRepository


}

class DefaultAppContainer(context: Context) : AppContainer {


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    private val retrofitService : MoshiPeliculaInterface by lazy {
        retrofit.create(MoshiPeliculaInterface::class.java) }

    override val peliculaRepository: PeliculaRepository by lazy {
        NetworkPeliculaRepository(retrofitService) }

    override val peliculaRepositoryDao: PeliculaRepositoryDao by lazy {
        PeliculaRepositoryDao(getDatabase(
            context,
            scope = CoroutineScope(kotlinx.coroutines.Dispatchers.IO)
        ).peliculaDao())
    }

    override val userPreferencesRepository = UserPreferencesRepository(UserPreferences(context))
}