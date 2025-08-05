package com.apps.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.apps.weatherapp.presentation.compose.CityDetailScreen
import com.apps.weatherapp.presentation.compose.SearchScreen
import com.apps.weatherapp.presentation.viewmodel.MainViewModel

@Composable
fun Navigation(viewModel: MainViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Search){
        composable<Routes.Search>{
            SearchScreen(viewModel, navController)
        }
        composable<Routes.City>{
            val args = it.toRoute<Routes.City>()
            CityDetailScreen(viewModel, args.id)
        }
    }
}