package com.cs4520.assignment4.data_layer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    const val ENDPOINT: String = "prod/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: IProductApi by lazy {
        retrofit.create(IProductApi::class.java)
    }

}