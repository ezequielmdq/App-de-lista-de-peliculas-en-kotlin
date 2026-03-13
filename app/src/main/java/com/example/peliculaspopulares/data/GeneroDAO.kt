package com.example.peliculaspopulares.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genero_table")
data class GeneroDAO(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    val id : String,
    @ColumnInfo(name = "name")
    val name : String

)

