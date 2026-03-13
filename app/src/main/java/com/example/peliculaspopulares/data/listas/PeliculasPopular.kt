package com.example.peliculaspopulares.data.listas

import com.squareup.moshi.Json



data class PeliculasPopular(

    val id : String,

    @Json(name = "original_title")
    val titulo : String?,

    @Json(name = "poster_path")
    val posterpath : String?,

    @Json(name = "backdrop_path")
    val backdrop : String?,



    )
