package com.unir.productoretrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getById(@Url url: String) : Response<ProductoResponse>
}