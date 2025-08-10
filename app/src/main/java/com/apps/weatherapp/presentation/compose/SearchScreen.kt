package com.apps.weatherapp.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.apps.weatherapp.presentation.navigation.Routes
import com.apps.weatherapp.presentation.viewmodel.MainViewModel
import com.apps.weatherapp.presentation.viewmodel.SearchUIState

@Composable
fun SearchScreen(viewModel: MainViewModel, navController: NavHostController) {

    val searchPrefix by viewModel.searchPrefix.collectAsStateWithLifecycle()
    val cityList by viewModel.cityList.collectAsStateWithLifecycle()
    val uiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchPrefix,
            onValueChange = { viewModel.changePrefix(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Search city")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        when(uiState){
            SearchUIState.Search -> {
                LazyColumn {
                    itemsIndexed(cityList) { index, item ->
                        CityListItem(item) {
                            navController.navigate(Routes.City(it))
                        }
                    }
                }
            }
            SearchUIState.Error -> {
                Text(text = "Unable to proceed. Please try again later")
            }
            SearchUIState.Default -> {
                //do nothing
            }
        }
    }
}