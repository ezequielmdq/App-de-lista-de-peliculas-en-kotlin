package com.example.peliculaspopulares.data.listasdaoid

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topID_table")
data class PeliculasTopDAOID(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    val id : String,
    @ColumnInfo(name = "original_title")
    val titulo : String?,
    @ColumnInfo(name = "overview")
    val descipcion : String?,
    @ColumnInfo(name = "vote_average")
    val porcenjatevotos : Float?,
    @ColumnInfo(name = "original_language")
    val lenguaje : String?,
    //@ColumnInfo(name = "genres")
    //val genero : List<Generodetalles>,
    @ColumnInfo(name = "release_date")
    val fechalanzamiento : String?,
    @ColumnInfo(name = "backdrop_path")
    val poster : String?

)
