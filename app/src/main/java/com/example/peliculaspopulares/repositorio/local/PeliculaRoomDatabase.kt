package com.example.peliculaspopulares.repositorio.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.peliculaspopulares.data.GeneroDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasNowDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasTopDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasPopularDAO
import com.example.peliculaspopulares.data.listasdao.PeliculasUpcomingDAO
import com.example.peliculaspopulares.data.listasdaoid.PeliculasNowDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasTopDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasPopularDAOID
import com.example.peliculaspopulares.data.listasdaoid.PeliculasUpcomingDAOID
import com.example.peliculaspopulares.service.PeliculaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



// Annotates class to be a Room Database with a table (entity) of the PeliculaDAO class
@Database(entities = [PeliculasPopularDAO::class,
                      PeliculasPopularDAOID::class,
                      PeliculasNowDAO::class,
                      PeliculasNowDAOID::class,
                      PeliculasTopDAO::class,
                      PeliculasTopDAOID::class,
                      PeliculasUpcomingDAO::class,
                      PeliculasUpcomingDAOID::class,
                      GeneroDAO::class], version = 12, exportSchema = false)
public abstract class PeliculaRoomDatabase : RoomDatabase(){

    abstract fun peliculaDao(): PeliculaDao

    private class PeliculaDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {

                    val peliculaDao = database.peliculaDao()

                    // Delete all content here.
                    peliculaDao.deleteAllPopular()

                    peliculaDao.deleteAllNow()

                    peliculaDao.deleteAllTop()

                    peliculaDao.deleteAllUpcoming()

                    peliculaDao.deleteAllPopularId()

                    peliculaDao.deleteAllNowId()

                    peliculaDao.deleteAllTopId()

                    peliculaDao.deleteAllUpcomingId()


                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PeliculaRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PeliculaRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeliculaRoomDatabase::class.java,
                    "pelicula_database"
                )
                    .addCallback(PeliculaDatabaseCallback(scope))
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}

/**
@Database(entities = [PeliculasPopularDAO::class, PeliculasPopularDAOID::class, GeneroDAO::class], version = 1, exportSchema = false)
abstract class PeliculaRoomDatabase : RoomDatabase() {

abstract fun peliculaDao(): PeliculaDao

companion object {

@Volatile
private var Instance: PeliculaRoomDatabase? = null

fun getDatabase(context: Context): PeliculaRoomDatabase {

return Instance ?: synchronized(this) {

Room.databaseBuilder(context, PeliculaRoomDatabase::class.java, "movies_database")
.fallbackToDestructiveMigration()
.build()
.also { Instance = it }

}

}
}


}

 */