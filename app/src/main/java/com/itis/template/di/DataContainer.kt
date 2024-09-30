package com.itis.template.di

import android.content.Context
import com.itis.template.BuildConfig
import com.itis.template.data.weather.datasource.remote.WeatherApi
import com.itis.template.data.weather.WeatherRepositoryImpl
import com.itis.template.data.core.interceptor.ApiKeyInterceptor
import com.itis.template.domain.weather.GetWeatherUseCase
import com.itis.template.utils.AndroidResourceProvider
import com.itis.template.utils.ResourceProvider
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object DataContainer {

    private const val BASE_URL = "base_url"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
    }

    private val weatherApi = retrofit.create(WeatherApi::class.java)

    private val weatherRepository = WeatherRepositoryImpl(weatherApi)

    val weatherUseCase: GetWeatherUseCase
        get() = GetWeatherUseCase(weatherRepository)

    fun provideResources(
        applicationContext: Context
    ): ResourceProvider = AndroidResourceProvider(applicationContext)
}
