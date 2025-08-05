package com.apps.weatherapp.repository

import com.apps.weatherapp.data.models.CityData
import com.apps.weatherapp.data.models.SearchModel
import retrofit2.Response

interface IMainRepository {

    suspend fun searchGivenPrefix(prefix: String): Response<List<SearchModel>>
    suspend fun getCityData(id: String): Response<CityData>
}