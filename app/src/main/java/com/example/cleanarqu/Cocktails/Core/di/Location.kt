package com.example.cleanarqu.Cocktails.Core.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import androidx.activity.ComponentActivity
//import com.example.cleanarqu.Cocktails.Model.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*
@Module
@InstallIn(SingletonComponent::class)
object Location {

    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext context: Context): LocationService = Location.provideLocationManager(context)
}

 */


/*
@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context
    ): ILocationService = LocationService(
        context,
        LocationServices.getFusedLocationProviderClient(context)
    )
}
 */