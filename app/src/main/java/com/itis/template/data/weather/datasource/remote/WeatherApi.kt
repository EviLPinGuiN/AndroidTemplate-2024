package com.itis.template.data.weather.datasource.remote

import com.itis.template.data.weather.datasource.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeather(
        @QueryMap map: Map<String, Any>
    ): WeatherResponse
}
