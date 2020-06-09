package com.example.myfirstapp.di

import kotlinx.coroutines.Dispatchers
import kz.jibergroup.studyinn.data.pref.PrefManager
import kz.jibergroup.studyinn.data.remote.TokenInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val appModule = module {

    /**
     * Coroutines setup
     */
    single<CoroutineContext>(named("io")) { Dispatchers.IO }
    single<CoroutineContext>(named("main")) { Dispatchers.Main }
    /**
     * Token interceptor
     */
    single { TokenInterceptor(get()) }

    single { PrefManager(androidContext()) }



}