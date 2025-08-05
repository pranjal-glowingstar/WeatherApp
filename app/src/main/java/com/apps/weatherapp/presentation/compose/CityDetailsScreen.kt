package com.apps.weatherapp.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.presentation.viewmodel.MainViewModel

@Composable
fun CityDetailScreen(viewModel: MainViewModel, id: String){

    val cityData by viewModel.cityData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCityData(id)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = cityData.Headline?.Text ?: AppConstants.EMPTY)
    }
}