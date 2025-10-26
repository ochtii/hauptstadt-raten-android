package com.example.hauptstadtraten

data class Country(
    val name: String,
    val capital: String,
    val alternativeCapitals: List<String> = emptyList(),
    val flag: String,
    val continent: String
) {
    fun getAllAcceptedCapitals(): List<String> {
        return listOf(capital) + alternativeCapitals
    }
}

data class CountryStats(
    val country: Country,
    var correctCount: Int = 0,
    var wrongCount: Int = 0
) {
    val totalAnswers: Int
        get() = correctCount + wrongCount
    
    val correctPercentage: Float
        get() = if (totalAnswers > 0) (correctCount.toFloat() / totalAnswers * 100) else 0f
}
