package com.itis.template.domain.weather

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        query: String
    ): WeatherInfo {
        log()
        test()
        log()
        return weatherRepository.getWeather(query)
    }

    fun log() {
        println("TEST")
    }

    fun test() {
        println("TEST")
    }
}