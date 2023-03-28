package com.afaneca.marvelchallenge.di

import com.afaneca.marvelchallenge.common.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region HTTP

    //endregion

    //region Repositories
    //endregion

    //region DB
    //endregion

    //region Data Sources

    //endregion

    //region misc
    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers(Dispatchers.IO)
    //endregion
}