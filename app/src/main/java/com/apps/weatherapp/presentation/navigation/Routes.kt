package com.apps.weatherapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Search: Routes()

    @Serializable
    data class City(val id: String): Routes()
}