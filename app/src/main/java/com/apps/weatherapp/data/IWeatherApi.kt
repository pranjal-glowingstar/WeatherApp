package com.apps.weatherapp.data

import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.data.models.CityData
import com.apps.weatherapp.data.models.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface IWeatherApi {

    @GET("/locations/v1/cities/search")
    suspend fun getSearchBasedOnPrefix(@QueryMap options: Map<String, String>): Response<List<SearchModel>>

    @GET("/forecasts/v1/daily/5day/{id}")
    suspend fun getCityData(@Path("id") id: String, @Query("apikey") apiKey: String = AppConstants.API_KEY): Response<CityData>
}