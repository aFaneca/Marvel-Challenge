package com.afaneca.marvelchallenge.di

import com.afaneca.marvelchallenge.BuildConfig
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.common.Constants
import com.afaneca.marvelchallenge.data.remote.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region HTTP
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // Enable HTTP logging for debug only
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient.Builder().addInterceptor(logging).addNetworkInterceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("apikey", BuildConfig.MARVEL_API_PUBLIC_KEY).build()

            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()
    }

    @Provides
    @Singleton
    fun provideMarvelApi(httpClient: OkHttpClient): MarvelApi =
        Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
            .create(MarvelApi::class.java)
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