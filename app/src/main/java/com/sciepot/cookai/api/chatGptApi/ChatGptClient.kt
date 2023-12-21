package com.sciepot.cookai.api.chatGptApi

import android.util.Log
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.sciepot.cookai.BuildConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal object ChatGptClient {
    private val openAI = OpenAI(BuildConfig.CHATGPT_API_KEY)
    private val modelID = ModelId("gpt-3.5-turbo")
    fun getRecipe(
        count: MutableStateFlow<Int>,
        query: String,
        recipe: MutableStateFlow<List<ResponseRecipe>>,
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch {
            try {
                val chatCompletionRequest = ChatCompletionRequest(
                    model = modelID,
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.Assistant,
                            content = query
                        )
                    )
                )
                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
                val response = completion.choices.first().message?.content ?: throw Exception("Error")
                val (a, b, c) = listOf(
                    response.indexOf("Name:"),
                    response.indexOf("Ingredients:"),
                    response.indexOf("Instructions:")
                )

                val name = response.substring(5, b).trim().takeIf { it != ""}
                val ingredients = response.substring(b + "Ingredients:".length, c).trim().takeIf { it != "" }
                val instructions = response.substring(c + "Instructions:".length, response.length).trim().takeIf { it != "" }

                recipe.value = recipe.value + listOf(ResponseRecipe(
                    name = name ?: throw Exception("name is blank for $response"),
                    ingredients = ingredients ?: throw Exception("ingredients are blank for $response"),
                    instructions = instructions ?: throw Exception("instructions are blank for $response")
                ))

            } catch (e: Exception) {
                Log.d("Chat GPT", "Exception:\n${e.message}")
            } finally {
                count.value++
            }
        }
    }
}