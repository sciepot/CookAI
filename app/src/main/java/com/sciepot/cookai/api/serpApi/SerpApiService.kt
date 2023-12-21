package com.sciepot.cookai.api.serpApi

import retrofit2.http.GET
import retrofit2.http.Query

interface SerpApiService {
    @GET("/search.json")
    suspend fun get(
        @Query("engine") engine: String,
        @Query("q") query: String,
        @Query("location") location: String,
        @Query("hl") hl: String,
        @Query("gl") gl: String,
        @Query("google_domain") googleDomain: String,
        @Query("api_key") apiKey: String
    ): String
}