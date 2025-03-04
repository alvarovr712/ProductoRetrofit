package com.unir.productoretrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {


    @GET
    suspend fun getAll(@Url url: String) : Response<ProductoCabecera>
}