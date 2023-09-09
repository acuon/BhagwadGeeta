package com.example.bhagwadgeeta.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.bhagwadgeeta.BuildConfig
import com.example.bhagwadgeeta.data.database.GeetaDao
import com.example.bhagwadgeeta.data.database.GeetaDatabase
import com.example.bhagwadgeeta.data.remote.GeetaService
import com.example.bhagwadgeeta.data.repository.GeetaRepository
import com.example.bhagwadgeeta.ui.home.viewmodel.GeetaViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGeetaService(retrofit: Retrofit): GeetaService {
        return retrofit.create(GeetaService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeetaRepository(
        geetaService: GeetaService,
        geetaDao: GeetaDao
    ): GeetaRepository {
        return GeetaRepository(geetaService, geetaDao)
    }

    @Provides
    @Singleton
    fun provideGeetaDao(database: GeetaDatabase): GeetaDao {
        return database.mathExpressionDao()
    }

    @Provides
    @Singleton
    fun provideGeetaDatabase(application: Application): GeetaDatabase {
        return Room.databaseBuilder(
            application,
            GeetaDatabase::class.java,
            "geeta_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGeetaViewModel(
        repository: GeetaRepository
    ): GeetaViewModel {
        return GeetaViewModel(repository)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", BuildConfig.API_KEY)
                .addHeader("X-RapidAPI-Host", BuildConfig.API_HOST)
                .build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
//            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//            else HttpLoggingInterceptor.Level.NONE
        }
    }
}