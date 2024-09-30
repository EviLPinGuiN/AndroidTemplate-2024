package com.itis.template.domain.weather

interface WeatherRepository {

    suspend fun getWeather(query: String): WeatherInfo
}

class WeatherRepositoryStub : WeatherRepository {
    override suspend fun getWeather(query: String): WeatherInfo {
        return WeatherInfo(
            temperature = 4.5,
            windSpeed = 6.7,
            windDeg = 8141,
            humidity = 4119,
            icon = "volumus"
        )
    }

}