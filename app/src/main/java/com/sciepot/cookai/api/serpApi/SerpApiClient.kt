package com.sciepot.cookai.api.serpApi

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://serpapi.com"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object SerpApiClient {
    val retrofitService: SerpApiService by lazy { retrofit.create(SerpApiService::class.java) }
}

