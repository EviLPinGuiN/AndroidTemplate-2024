package com.itis.template.data.weather.mapper

import com.itis.template.data.weather.datasource.remote.response.WeatherResponse
import com.itis.template.domain.weather.WeatherInfo

fun WeatherResponse.toWeatherInfo(): WeatherInfo = WeatherInfo(
    temperature = main.temp,
    windSpeed = wind.speed,
    windDeg = wind.deg,
    humidity = main.humidity,
    icon = weather.first().icon
)

fun List<WeatherResponse>.toWeathers(): List<WeatherInfo> = map {
    it.toWeatherInfo()
}
