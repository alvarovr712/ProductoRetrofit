package com.unir.productoretrofit

data class ProductoCabecera(

    val page:Int,
    val per_page:Int,
    val total:Int,
    val total_pages:Int,
    val results: List<ProductoResponse>
)
