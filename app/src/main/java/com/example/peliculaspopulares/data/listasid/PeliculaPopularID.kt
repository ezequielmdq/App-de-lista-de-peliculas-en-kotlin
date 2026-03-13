package com.example.peliculaspopulares.data.listasid


import com.example.peliculaspopulares.data.Generodetalles
import com.squareup.moshi.Json

data class PeliculaPopularID(



    val id : String,
    //@SerializedName("original_title")
    @Json(name = "original_title")
    val titulo : String?,
    //@SerializedName("overview")
    @Json(name = "overview")
    val descipcion : String?,
    //@SerializedName("vote_average")
    @Json(name = "vote_average")
    val porcenjatevotos : Float?,
    //@SerializedName("original_language")
    @Json(name = "original_language")
    val lenguaje : String?,
    //@SerializedName("genres")
    @Json(name = "genres")
    val genero : List<Generodetalles>?,
    //@SerializedName("release_date")
    @Json(name = "release_date")
    val fechalanzamiento : String?,
    //@SerializedName("backdrop_path")
    @Json(name = "backdrop_path")
    val poster : String?




    )
