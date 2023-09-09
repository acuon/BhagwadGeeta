package com.example.bhagwadgeeta.di

import android.app.Application
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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

}