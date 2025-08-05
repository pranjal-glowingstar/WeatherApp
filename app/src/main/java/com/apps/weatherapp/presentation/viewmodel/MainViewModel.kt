package com.apps.weatherapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.weatherapp.common.DispatcherUtil
import com.apps.weatherapp.data.models.CityData
import com.apps.weatherapp.data.models.SearchModel
import com.apps.weatherapp.repository.IMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext val context: Context, val repository: IMainRepository): ViewModel() {

    private val _searchPrefix = MutableStateFlow("")
    private val _cityList = MutableStateFlow(listOf<SearchModel>())
    private val _cityData = MutableStateFlow(CityData())


    val searchPrefix = _searchPrefix.asStateFlow()
    val cityList = _cityList.asStateFlow()
    val cityData = _cityData.asStateFlow()

    fun searchGivenPrefix(){
        if(_searchPrefix.value.isNotEmpty()){
            viewModelScope.launch(DispatcherUtil.getIODispatcher()) {
                val response = repository.searchGivenPrefix(_searchPrefix.value)
                if(response.isSuccessful){
                    response.body()?.let{
                        _cityList.value = it
                    }
                }
            }
        }
    }
    fun getCityData(id: String){
        viewModelScope.launch(DispatcherUtil.getIODispatcher()) {
            val response = repository.getCityData(id)
            if(response.isSuccessful){
                _cityData.value = response.body()!!
            }
        }
    }
    fun changePrefix(value: String){
        _searchPrefix.value = value
    }

}