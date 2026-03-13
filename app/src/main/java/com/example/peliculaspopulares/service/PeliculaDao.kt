package com.example.peliculaspopulares.service


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.peliculaspopulares.data.listasdao.PeliculasNowDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasPopularDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasTopDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasUpcomingDAO
import com.example.peliculaspopulares.data.listasdaoid.PeliculasNowDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasPopularDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasTopDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasUpcomingDAOID
import kotlinx.coroutines.flow.Flow


@Dao
interface PeliculaDao {

    @Query("SELECT * FROM popular_table")
    fun getPeliculasPopular() : Flow<List<PeliculasPopularDAO>>

    @Query("SELECT * FROM now_table")
    fun getPeliculasNow() : Flow<List<PeliculasNowDAO>>

    @Query("SELECT * FROM top_table")
    fun getPeliculasTop() : Flow<List<PeliculasTopDAO>>

    @Query("SELECT * FROM upcoming_table")
    fun getPeliculasUpcoming() : Flow<List<PeliculasUpcomingDAO>>

    @Query("SELECT ID, original_title, vote_average, original_language, release_date, backdrop_path, overview FROM popularID_table WHERE ID = :id")
    fun getDetallePopular(id: String) : Flow<PeliculasPopularDAOID>

    @Query("SELECT ID, original_title, vote_average, original_language, release_date, backdrop_path, overview FROM nowID_table WHERE ID = :id")
    fun getDetalleNow(id: String) : Flow<PeliculasNowDAOID>

    @Query("SELECT ID, original_title, vote_average, original_language, release_date, backdrop_path, overview FROM topID_table WHERE ID = :id")
    fun getDetalleTop(id: String) : Flow<PeliculasTopDAOID>

    @Query("SELECT ID, original_title, vote_average, original_language, release_date, backdrop_path, overview FROM upcomingID_table WHERE ID = :id")
    fun getDetalleUpcoming(id: String) : Flow<PeliculasUpcomingDAOID>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPopular(pelicula: List<PeliculasPopularDAO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNow(pelicula: List<PeliculasNowDAO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTop(pelicula: List<PeliculasTopDAO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUpcoming(pelicula: List<PeliculasUpcomingDAO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPopularID(pelicula: List<PeliculasPopularDAOID>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNowID(pelicula: List<PeliculasNowDAOID>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTopID(pelicula: List<PeliculasTopDAOID>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUpcomingID(pelicula: List<PeliculasUpcomingDAOID>)

    @Query("DELETE FROM popular_table")
    suspend fun deleteAllPopular()

    @Query("DELETE FROM now_table")
    suspend fun deleteAllNow()

    @Query("DELETE FROM top_table")
    suspend fun deleteAllTop()

    @Query("DELETE FROM upcoming_table")
    suspend fun deleteAllUpcoming()

    @Query("DELETE FROM popularID_table")
    suspend fun deleteAllPopularId()

    @Query("DELETE FROM nowID_table")
    suspend fun deleteAllNowId()

    @Query("DELETE FROM topID_table")
    suspend fun deleteAllTopId()

    @Query("DELETE FROM upcomingID_table")
    suspend fun deleteAllUpcomingId()

}