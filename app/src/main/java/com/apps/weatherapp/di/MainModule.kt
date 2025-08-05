package com.apps.weatherapp.di

import com.apps.weatherapp.common.AppConstants
import com.apps.weatherapp.data.IMainNetworkService
import com.apps.weatherapp.repository.IMainRepository
import com.apps.weatherapp.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun providesNetworkInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providesNetworkImplementation(network: Retrofit): IMainNetworkService{
        return network.create(IMainNetworkService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MainAbstractModule{

    @Binds
    @Singleton
    abstract fun providesRepository(repositoryImpl: MainRepositoryImpl): IMainRepository
}