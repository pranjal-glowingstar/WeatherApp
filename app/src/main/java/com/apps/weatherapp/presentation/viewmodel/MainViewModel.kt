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