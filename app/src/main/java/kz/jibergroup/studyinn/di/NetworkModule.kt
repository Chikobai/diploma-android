package com.example.myfirstapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kz.jibergroup.studyinn.data.remote.ApiService
import kz.jibergroup.studyinn.data.remote.TokenInterceptor
import kz.jibergroup.studyinn.utils.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<OkHttpClient>(named("default")) { provideDefaultOkhttpClient() }
    single<Retrofit>(named("default_retrofit")) { provideDefaultRetrofit(get(named("default"))) }


    single<OkHttpClient>(named("token")) { provideTokenOkhttpClient(get()) }
    single<Retrofit>(named("token_retrofit")) { provideTokenRetrofit(get(named("token"))) }
    single { provideApiService(get(named("token_retrofit"))) }


}

fun provideDefaultOkhttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideTokenOkhttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(tokenInterceptor)
        .build()
}

fun provideDefaultRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideTokenRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
