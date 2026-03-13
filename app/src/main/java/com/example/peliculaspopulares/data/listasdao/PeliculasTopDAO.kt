package com.example.peliculaspopulares.data.listasdao

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_table")
data class PeliculasTopDAO(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    @NonNull
    val id: String,

    @ColumnInfo(name = "original_title")
    val titulo: String?,

    @ColumnInfo(name = "poster_path")
    val posterpath: String?,

    @ColumnInfo(name = "backdrop_path")
    val backdrop: String?,

    )
