package com.sciepot.cookai.api.serpApi

import com.google.gson.JsonParser
import com.sciepot.cookai.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SerpApiRepository {
    private val engine = "google_images"
    private val location = "Austin,+TX,+Texas,+United+States"
    private val hl = "en"
    private val gl = "us"
    private val googleDomain = "google.com"
    private val apiKey = BuildConfig.GOOGLE_SERP_API_KEY
    private val jsonArray = "images_results"
    private val jsonField = "original"

    suspend fun get (name: String, onComplete: (List<String>) -> Unit) {
        try {
            val rawJson = SerpApiClient.retrofitService.get(
                engine = engine,
                query = name,
                location = location,
                hl = hl,
                gl = gl,
                googleDomain = googleDomain,
                apiKey = apiKey)
            val results = JsonParser.parseString(rawJson).asJsonObject.getAsJsonArray(jsonArray)

            withContext(Dispatchers.Main) {
                when (results.size()) {
                    0 -> throw Exception()
                    1 -> onComplete(listOf(
                        results.get(0).asJsonObject.getAsJsonPrimitive(jsonField)?.asString ?: "", "", ""))
                    2 -> onComplete(listOf(
                        results.get(0).asJsonObject.getAsJsonPrimitive(jsonField)?.asString ?: "",
                        results.get(1).asJsonObject.getAsJsonPrimitive(jsonField)?.asString ?: "", ""))
                    else -> onComplete(
                            listOf(
                                results.get(0).asJsonObject.getAsJsonPrimitive(jsonField)?.asString ?: "",
                                results.get(1).asJsonObject.getAsJsonPrimitive(jsonField)?.asString ?: "",
                                results.get(2).asJsonObject.getAsJsonPrimitive(jsonField)?.asString ?: ""))
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onComplete(listOf("", "", ""))
            }
        }
    }
}