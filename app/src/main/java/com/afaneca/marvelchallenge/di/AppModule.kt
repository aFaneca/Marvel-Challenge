package com.afaneca.marvelchallenge.di

import android.content.Context
import androidx.room.Room
import com.afaneca.marvelchallenge.BuildConfig
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.common.Constants
import com.afaneca.marvelchallenge.data.local.MarvelLocalDataSource
import com.afaneca.marvelchallenge.data.local.MockTopSellingLocalDataSource
import com.afaneca.marvelchallenge.data.local.RoomMarvelLocalDataSource
import com.afaneca.marvelchallenge.data.local.TopSellingLocalDataSource
import com.afaneca.marvelchallenge.data.local.db.MarvelDatabase
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDao
import com.afaneca.marvelchallenge.data.remote.ApiMarvelRemoteDataSource
import com.afaneca.marvelchallenge.data.remote.MarvelApi
import com.afaneca.marvelchallenge.data.remote.MarvelRemoteDataSource
import com.afaneca.marvelchallenge.data.repository.LiveCharacterRepository
import com.afaneca.marvelchallenge.data.repository.LiveTopSellingRepository
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import com.afaneca.marvelchallenge.domain.repository.TopSellingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                // Inject "apikey" param into every request
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apikey", BuildConfig.MARVEL_API_PUBLIC_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }.addInterceptor { chain ->
                // Inject "referer" header into every outgoing request
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("referer", Constants.API_REFERER_DOMAIN)
                }.build())
            }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideMarvelApi(httpClient: OkHttpClient): MarvelApi =
        Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
            .create(MarvelApi::class.java)
    //endregion

    //region Repositories
    @Provides
    @Singleton
    fun provideCharacterRepository(
        localDataSource: MarvelLocalDataSource,
        remoteDataSource: MarvelRemoteDataSource,
    ): CharacterRepository = LiveCharacterRepository(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideTopSellingRepository(
        localDataSource: TopSellingLocalDataSource,
    ): TopSellingRepository = LiveTopSellingRepository(localDataSource)
    //endregion

    //region DB
    @Provides
    @Singleton
    fun provideMarvelDatabase(@ApplicationContext context: Context): MarvelDatabase =
        Room.databaseBuilder(context, MarvelDatabase::class.java, "marvel-db").build()

    @Provides
    @Singleton
    fun provideCharacterDao(db: MarvelDatabase) = db.characterDao()
    //endregion

    //region Data Sources
    @Provides
    @Singleton
    fun provideMarvelRemoteDataSource(
        marvelApi: MarvelApi
    ): MarvelRemoteDataSource = ApiMarvelRemoteDataSource(marvelApi)

    @Provides
    @Singleton
    fun provideTopSellingLocalDataSource(): TopSellingLocalDataSource =
        MockTopSellingLocalDataSource()

    @Provides
    @Singleton
    fun provideMarvelLocalDataSource(
        characterDao: CharacterDao
    ): MarvelLocalDataSource = RoomMarvelLocalDataSource(characterDao)
    //endregion

    //region misc
    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers(Dispatchers.IO)
    //endregion
}