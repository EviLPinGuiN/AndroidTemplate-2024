package com.itis.template.presentation.mvvm

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itis.template.di.DataContainer
import com.itis.template.domain.weather.GetWeatherUseCase
import com.itis.template.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val getWeatherUseCase2: GetWeatherUseCase =
        DataContainer.weatherUseCase

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _weatherInfo = MutableLiveData<WeatherInfo?>(null)
    val weatherInfo: LiveData<WeatherInfo?>
        get() = _weatherInfo

    val navigateDetails = MutableLiveData<Boolean?>(null)

    private val _loadingFlow = MutableStateFlow<Boolean>(false)
    val loadingFlow: StateFlow<Boolean>
        get() = _loadingFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow<Throwable?>(null)
    val errorFlow: StateFlow<Throwable?>
        get() = _errorFlow.asStateFlow()

    private val _weatherInfoFlow = MutableStateFlow<WeatherInfo?>(null)
    val weatherInfoFlow: StateFlow<WeatherInfo?>
        get() = _weatherInfoFlow.asStateFlow()

    fun onLoadClick(query: String) {
        loadWeather(query)
    }

    val weatherInfoFlowState: StateFlow<WeatherInfo?> = flowOf("Kazan")
        .mapLatest {
            getWeatherUseCase(it)
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = null
        )

    private fun loadWeather(query: String) {
        viewModelScope.launch {
            try {
                _loadingFlow.emit(true)
                getWeatherUseCase(query).also { weatherInfo ->
                    _weatherInfoFlow.emit(weatherInfo)
                }
            } catch (error: Throwable) {
                _errorFlow.emit(error)
            } finally {
                _loadingFlow.emit(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val useCase = DataContainer.weatherUseCase
                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
                return MainViewModel(useCase) as T
            }
        }

        val FactoryExt: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val useCase = DataContainer.weatherUseCase
                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
                MainViewModel(useCase)
            }
        }

    }
}