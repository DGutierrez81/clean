package com.example.cleanarqu.Cocktails.Core.di

import com.example.cleanarqu.Cocktails.Model.CocktailsView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCocktailsApiService(retrofit: Retrofit): CocktailsView {
        return retrofit.create(CocktailsView::class.java )
    }
}