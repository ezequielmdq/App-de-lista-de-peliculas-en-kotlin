package com.example.peliculaspopulares.data.paginas

import com.example.peliculaspopulares.data.listas.PeliculasTop


data class PaginaTop(

    val page: String,
    val results : List<PeliculasTop>,

    )
