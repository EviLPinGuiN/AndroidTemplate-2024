package com.itis.template.data.weather

import com.itis.template.data.weather.datasource.remote.WeatherApi
import com.itis.template.data.weather.mapper.toWeatherInfo
import com.itis.template.domain.weather.WeatherInfo
import com.itis.template.domain.weather.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherDefault: CoroutineDispatcher = Dispatchers.Default
) : WeatherRepository {

    override suspend fun getWeather(
        query: String
    ): WeatherInfo = withContext(dispatcherIO) {
        api.getWeather(query).toWeatherInfo()
    }


}