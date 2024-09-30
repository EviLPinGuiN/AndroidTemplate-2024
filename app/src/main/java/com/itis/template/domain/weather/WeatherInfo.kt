package com.itis.template.domain.weather

data class WeatherInfo(
    val temperature: Double,
    val windSpeed: Double,
    val windDeg: Int,
    val humidity: Int,
    val icon: String,
    val cloud: Cloud? = null,
)

data class Cloud(
    val perc: Int
)
