package com.apps.weatherapp.presentation.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.data.models.SearchModel

@Composable
fun CityListItem(item: SearchModel, onCityClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable { onCityClick(item.Key!!) }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text = item.EnglishName ?: AppConstants.EMPTY)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = item.Country?.EnglishName ?: AppConstants.EMPTY)
            Text(text = item.Region?.EnglishName ?: AppConstants.EMPTY)
            Text(text = item.PrimaryPostalCode ?: AppConstants.EMPTY)
        }
    }
}