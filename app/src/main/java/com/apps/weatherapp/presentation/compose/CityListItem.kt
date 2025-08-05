package com.apps.weatherapp.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.data.models.SearchModel

@Composable
fun CityListItem(item: SearchModel, onCityClick: (String) -> Unit){
    Row(modifier = Modifier.fillMaxSize().clickable{ onCityClick(item.Key!!) }) {
        Text(text = item.Key ?: AppConstants.EMPTY)
    }
}