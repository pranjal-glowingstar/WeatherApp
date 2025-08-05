package com.apps.weatherapp.common

import kotlinx.coroutines.Dispatchers

object DispatcherUtil {

    private val IO = Dispatchers.IO
    private val Main = Dispatchers.Main

    fun getIODispatcher() = IO
    fun getMainDispatcher() = Main
}