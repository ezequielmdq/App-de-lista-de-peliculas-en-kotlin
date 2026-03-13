package com.example.peliculaspopulares.data.paginas

import com.example.peliculaspopulares.data.listas.PeliculasUpcoming


data class PaginaUpcoming(

    val page: String,
    val results : List<PeliculasUpcoming>,

    )
