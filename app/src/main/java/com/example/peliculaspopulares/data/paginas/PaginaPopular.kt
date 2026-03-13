package com.example.peliculaspopulares.data.paginas

import com.example.peliculaspopulares.data.listas.PeliculasPopular


data class PaginaPopular(

    val page: String,
    val results : List<PeliculasPopular>,

    )
