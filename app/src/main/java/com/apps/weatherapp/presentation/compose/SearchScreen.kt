package com.apps.weatherapp.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.apps.weatherapp.presentation.navigation.Routes
import com.apps.weatherapp.presentation.viewmodel.MainViewModel

@Composable
fun SearchScreen(viewModel: MainViewModel, navController: NavHostController){

    val searchPrefix by viewModel.searchPrefix.collectAsStateWithLifecycle()
    val cityList by viewModel.cityList.collectAsStateWithLifecycle()

    LaunchedEffect(searchPrefix) {
        viewModel.searchGivenPrefix()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = searchPrefix, onValueChange = { viewModel.changePrefix(it)})
        if(cityList.isNotEmpty()){
            LazyColumn {
                itemsIndexed(cityList){ index, item ->
                    CityListItem(item){
                        navController.navigate(Routes.City(it))
                    }
                }
            }
        }
    }
}