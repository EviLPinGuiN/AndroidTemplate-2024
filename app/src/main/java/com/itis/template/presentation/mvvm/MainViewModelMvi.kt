package com.itis.template.presentation.mvvm

import android.provider.ContactsContract.Data
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itis.template.R
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

class MainViewModelMvi(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val getWeatherUseCase2: GetWeatherUseCase =
        DataContainer.weatherUseCase

    private val _state: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _stateData: MutableStateFlow<ScreenStateData> = MutableStateFlow(ScreenStateData())
    val stateData = _stateData.asStateFlow()

    fun onLoadClick(query: String) {
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

    override fun onCleared() {
        super.onCleared()

    }

    fun reducer(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnQueryChange -> onQueryChange(event.query)
            is ScreenEvent.OnQueryChange2 -> TODO()
            is ScreenEvent.OnQueryChange3 -> TODO()
            is ScreenEvent.OnQueryChange4 -> TODO()
            is ScreenEvent.OnQueryChange5 -> TODO()
            is ScreenEvent.OnQueryChange6 -> TODO()
            is ScreenEvent.OnCreate -> onCreate(event.id)
            is ScreenEvent.OnLoadButtonClick -> onQueryChange(event.query)
        }
    }

    private fun onCreate(id: Int) {

    }

    private fun onQueryChange(query: String) {
        viewModelScope.launch {
            try {
                _state.emit(ScreenState.Loading(true))

                _stateData.emit(_stateData.value.copy(
                    isLoading = true
                ))
                getWeatherUseCase(query).also { weatherInfo ->
                    _state.emit(ScreenState.SuccessData(weatherInfo))

                    _stateData.emit(_stateData.value.copy(
                        weatherInfo = weatherInfo
                    ))
                }
            } catch (error: Throwable) {
                _state.emit(ScreenState.Error(error))

                _stateData.emit(_stateData.value.copy(
                    error = error
                ))
            } finally {
                _state.emit(ScreenState.Loading(false))

                _stateData.emit(_stateData.value.copy(
                    isLoading = false
                ))
            }
        }
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
                return MainViewModelMvi(useCase) as T
            }
        }

        val FactoryExt: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val useCase = DataContainer.weatherUseCase
                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
                MainViewModelMvi(useCase)
            }
        }

    }

    sealed interface ScreenEvent {
        data class OnCreate(val id: Int) : ScreenEvent
        data class OnQueryChange(val query: String) : ScreenEvent
        data class OnQueryChange2(val query: String) : ScreenEvent
        data class OnQueryChange3(val query: String) : ScreenEvent
        data class OnQueryChange4(val query: String) : ScreenEvent
        data class OnQueryChange5(val query: String) : ScreenEvent
        data class OnQueryChange6(val query: String) : ScreenEvent
        data class OnLoadButtonClick(val query: String) : ScreenEvent
    }

    sealed interface ScreenState {
        object Initial : ScreenState
        data class Loading(val isLoading: Boolean) : ScreenState
        data class UserList(val users: List<String>) : ScreenState
        data class Error(val throwable: Throwable) : ScreenState
        data class SuccessData(val weatherInfo: WeatherInfo) : ScreenState
    }

    data class ScreenStateData(
        val title: String = "My title",
        @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground,
        val isLoading: Boolean = false,
        val weatherInfo: WeatherInfo? = null,
        val error: Throwable? = null,
        val users: List<String> = arrayListOf(),
    )

    sealed interface ScreenCommand {
        data class ShowToast(val text: String) : ScreenCommand
    }
}