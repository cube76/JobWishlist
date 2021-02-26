package com.mqa.jobwishlist.core.di

import com.mqa.jobwishlist.core.data.source.remote.network.ApiService
import com.mqa.jobwishlist.core.utils.Constant.Companion.BASE_URL
import com.mqa.jobwishlist.core.utils.Constant.Companion.HOSTNAME
import com.mqa.jobwishlist.core.utils.Constant.Companion.PIN
import com.mqa.jobwishlist.core.utils.Constant.Companion.USERNAME_BASIC_AUTH
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOSTNAME, PIN)
            .build()
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(BasicAuthInterceptor(USERNAME_BASIC_AUTH,""))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}