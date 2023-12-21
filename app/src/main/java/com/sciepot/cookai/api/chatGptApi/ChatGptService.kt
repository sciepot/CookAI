package com.sciepot.cookai.api.chatGptApi

import com.sciepot.cookai.data.ingredients.Ingredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class ChatGptService() {

    fun suggest (
        maxRequests: Int,
        ingredients: List<Ingredient>,
        count: MutableStateFlow<Int>,
        coroutineScope: CoroutineScope
    ): List<ResponseRecipe> {
        val requestMessage = "Suggest a recipe for the following ingredients: "
        val formatMessage = ". The format of the response should follow below pattern:\n\n" +
                "Name: <Name of a recipe>\n" +
                "Ingredients:\n" +
                "300 g Parmesan\n" +
                "500 ml Water\n" +
                "...\n" +
                "Instructions: \n" +
                "1. Boil a water...\n" +
                "2. Slice tomatoes...\n\n" +
                "\"Name:\", \"Ingredients:\", and \"Instructions:\" must be PRESENT and CAPITALIZED!!!\n" +
                "Your response will be used in REGULAR EXPRESSION, so DO NOT WRITE any off topic information\n" +
                "Thank you for your cooperation!"

        val queries = MutableList(maxRequests) { requestMessage }
        val recipes = MutableStateFlow<List<ResponseRecipe>>(emptyList())
        for (id in 0 until maxRequests) {
            for (ingredient in ingredients.shuffled()) {
                queries[id] += "${ingredient.name} ${ingredient.quantity} ${ingredient.originalUnit.show},"
            }
            queries[id].dropLast(1)
            queries[id] += formatMessage
            ChatGptClient.getRecipe(count, queries[id], recipes, coroutineScope)
        }
        while (count.value < maxRequests) {}
        return recipes.value
    }
}