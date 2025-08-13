package com.apps.weatherapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.common.DispatcherUtil
import com.apps.weatherapp.data.models.CityData
import com.apps.weatherapp.data.models.SearchModel
import com.apps.weatherapp.repository.IMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext val context: Context, val repository: IMainRepository): ViewModel() {

    private val _searchPrefix = MutableStateFlow("")
    private val _cityList = MutableStateFlow(listOf<SearchModel>())
    private val _cityData = MutableStateFlow(CityData())
    private val _searchUiState: MutableStateFlow<SearchUIState> = MutableStateFlow(SearchUIState.Default)
    private val _cityUiState: MutableStateFlow<CityUIState> = MutableStateFlow(CityUIState.Default)


    val searchPrefix = _searchPrefix.asStateFlow()
    val cityList = _cityList.asStateFlow()
    val cityData = _cityData.asStateFlow()
    val searchUiState = _searchUiState.asStateFlow()
    val cityUiState = _cityUiState.asStateFlow()

    init {
        viewModelScope.launch(DispatcherUtil.getIODispatcher()) {
            _searchPrefix.debounce(AppConstants.SEARCH_DEBOUNCE_TIME).collectLatest {
                searchGivenPrefix(it)
            }
        }
    }

    private fun searchGivenPrefix(value: String){
        if(value.isNotEmpty()){
            viewModelScope.launch(DispatcherUtil.getIODispatcher()) {
                try {
                    val response = repository.searchGivenPrefix(_searchPrefix.value)
                    if(response.isSuccessful){
                        response.body()?.let{
                            if(it.isNotEmpty()){
                                _cityList.value = it.distinct()
                                _searchUiState.value = SearchUIState.Search
                            } else {
                                _searchUiState.value = SearchUIState.Error
                            }
                        } ?: run {
                            _searchUiState.value = SearchUIState.Error
                        }
                    } else {
                        _searchUiState.value = SearchUIState.Error
                    }
                } catch (e: Exception){
                    _searchUiState.value = SearchUIState.Error
                }
            }
        }
    }
    fun getCityData(id: String){
        viewModelScope.launch(DispatcherUtil.getIODispatcher()) {
            try {
                val response = repository.getCityData(id)
                if(response.isSuccessful){
                    response.body()?.let {
                        _cityData.value = it
                        _cityUiState.value = CityUIState.Display
                    } ?: run {
                        _cityUiState.value = CityUIState.Error
                    }
                } else {
                    _cityUiState.value = CityUIState.Error
                }
            } catch (e: Exception){
                _cityUiState.value = CityUIState.Error
            }
        }
    }
    fun changePrefix(value: String){
        _searchPrefix.value = value
    }
}
sealed class SearchUIState{
    data object Error: SearchUIState()
    data object Search: SearchUIState()
    data object Default: SearchUIState()
}
sealed class CityUIState{
    data object Error: CityUIState()
    data object Display: CityUIState()
    data object Default: CityUIState()
}
//CityData(Headline=Headline(EffectiveDate=2025-08-12T07:00:00+05:30, EffectiveEpochDate=1754962200, Severity=5, Text=A thunderstorm Tuesday, Category=thunderstorm, EndDate=2025-08-12T19:00:00+05:30, EndEpochDate=1755005400, MobileLink=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?lang=en-us, Link=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?lang=en-us), DailyForecasts=[DailyForecasts(Date=2025-08-12T07:00:00+05:30, EpochDate=1754962200, Temperature=Temperature(Minimum=Minimum(Value=81, Unit=F, UnitType=18), Maximum=Maximum(Value=93, Unit=F, UnitType=18)), Day=Day(Icon=15, IconPhrase=Thunderstorms, HasPrecipitation=true), Night=Night(Icon=42, IconPhrase=Mostly cloudy w/ t-storms, HasPrecipitation=true, PrecipitationType=Rain, PrecipitationIntensity=Heavy), Sources=[AccuWeather], MobileLink=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=1&lang=en-us, Link=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=1&lang=en-us), DailyForecasts(Date=2025-08-13T07:00:00+05:30, EpochDate=1755048600, Temperature=Temperature(Minimum=Minimum(Value=80, Unit=F, UnitType=18), Maximum=Maximum(Value=89, Unit=F, UnitType=18)), Day=Day(Icon=12, IconPhrase=Showers, HasPrecipitation=true), Night=Night(Icon=12, IconPhrase=Showers, HasPrecipitation=true, PrecipitationType=Rain, PrecipitationIntensity=Moderate), Sources=[AccuWeather], MobileLink=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=2&lang=en-us, Link=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=2&lang=en-us), DailyForecasts(Date=2025-08-14T07:00:00+05:30, EpochDate=1755135000, Temperature=Temperature(Minimum=Minimum(Value=79, Unit=F, UnitType=18), Maximum=Maximum(Value=90, Unit=F, UnitType=18)), Day=Day(Icon=18, IconPhrase=Rain, HasPrecipitation=true), Night=Night(Icon=40, IconPhrase=Mostly cloudy w/ showers, HasPrecipitation=true, PrecipitationType=Rain, PrecipitationIntensity=Light), Sources=[AccuWeather], MobileLink=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=3&lang=en-us, Link=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=3&lang=en-us), DailyForecasts(Date=2025-08-15T07:00:00+05:30, EpochDate=1755221400, Temperature=Temperature(Minimum=Minimum(Value=81, Unit=F, UnitType=18), Maximum=Maximum(Value=92, Unit=F, UnitType=18)), Day=Day(Icon=12, IconPhrase=Showers, HasPrecipitation=true), Night=Night(Icon=35, IconPhrase=Partly cloudy, HasPrecipitation=false, PrecipitationType=null, PrecipitationIntensity=null), Sources=[AccuWeather], MobileLink=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=4&lang=en-us, Link=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=4&lang=en-us), DailyForecasts(Date=2025-08-16T07:00:00+05:30, EpochDate=1755307800, Temperature=Temperature(Minimum=Minimum(Value=82, Unit=F, UnitType=18), Maximum=Maximum(Value=94, Unit=F, UnitType=18)), Day=Day(Icon=12, IconPhrase=Showers, HasPrecipitation=true), Night=Night(Icon=35, IconPhrase=Partly cloudy, HasPrecipitation=false, PrecipitationType=null, PrecipitationIntensity=null), Sources=[AccuWeather], MobileLink=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=5&lang=en-us, Link=http://www.accuweather.com/en/in/lucknow/206678/daily-weather-forecast/206678?day=5&lang=en-us)])