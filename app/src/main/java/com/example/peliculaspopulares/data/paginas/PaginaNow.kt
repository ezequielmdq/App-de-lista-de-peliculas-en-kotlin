package com.example.peliculaspopulares.data.paginas

import com.example.peliculaspopulares.data.listas.PeliculasNow


data class PaginaNow(

    val page: String,
    val results : List<PeliculasNow>,

    )
