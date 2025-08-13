package com.apps.weatherapp.repository

import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.data.IWeatherApi
import com.apps.weatherapp.data.models.CityData
import com.apps.weatherapp.data.models.SearchModel
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val network: IWeatherApi): IMainRepository {
    override suspend fun searchGivenPrefix(prefix: String): Response<List<SearchModel>> {
        return network.getSearchBasedOnPrefix(mapOf(Pair("apikey", AppConstants.API_KEY), Pair("q", prefix)))
    }

    override suspend fun getCityData(id: String): Response<CityData> {
        return network.getCityData(id)
    }
}