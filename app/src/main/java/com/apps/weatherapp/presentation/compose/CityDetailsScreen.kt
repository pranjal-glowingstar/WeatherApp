package com.apps.weatherapp.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.weatherapp.presentation.viewmodel.MainViewModel

@Composable
fun CityDetailScreen(viewModel: MainViewModel, id: String){

    val cityData by viewModel.cityData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCityData(id)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = cityData.Headline?.EffectiveDate.toString())
        Text(text = cityData.Headline?.EffectiveEpochDate.toString())
        Text(text = cityData.Headline?.Severity.toString())
        Text(text = cityData.Headline?.Text.toString())
        Text(text = cityData.Headline?.Category.toString())
        Text(text = cityData.Headline?.EndDate.toString())
        Text(text = cityData.Headline?.EndEpochDate.toString())
        Text(text = cityData.Headline?.MobileLink.toString())
        Text(text = cityData.Headline?.Link.toString())
        Text(text = cityData.Headline?.Link.toString())
        Spacer(modifier = Modifier.padding(top = 36.dp))
        cityData.DailyForecasts.forEach { item ->
            Text(text = item.Date.toString())
            Text(text = item.EpochDate.toString())
            Text(text = item.Temperature?.Maximum.toString())
            Text(text = item.Temperature?.Minimum.toString())
            Text(text = item.Day?.Icon.toString())
            Text(text = item.Day?.IconPhrase.toString())
            Text(text = item.Day?.HasPrecipitation.toString())
            Text(text = item.Night?.Icon.toString())
            Text(text = item.Night?.IconPhrase.toString())
            Text(text = item.Night?.HasPrecipitation.toString())
            Text(text = item.Night?.PrecipitationType.toString())
            Text(text = item.Night?.PrecipitationIntensity.toString())
            Text(text = item.MobileLink.toString())
            Text(text = item.Link.toString())
        }
    }
}