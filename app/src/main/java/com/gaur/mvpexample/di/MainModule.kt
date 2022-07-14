package com.gaur.mvpexample.di

import com.gaur.mvpexample.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object MainModule {
    
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder().baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }

}