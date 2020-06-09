package kz.jibergroup.studyinn.data.remote

import android.annotation.SuppressLint
import kotlinx.coroutines.runBlocking
import kz.jibergroup.studyinn.data.pref.PrefManager
import kz.jibergroup.studyinn.domain.ApiCaller
import kz.jibergroup.studyinn.domain.CoroutineCaller
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import timber.log.Timber
import java.io.IOException

class TokenInterceptor(private val prefManager: PrefManager) : Interceptor, KoinComponent,
    CoroutineCaller by ApiCaller() {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? = runBlocking {
        token = "Bearer " + prefManager.getAccessToken()
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        setAuthHeader(
            requestBuilder,
            token
        )
        val response = chain.proceed(requestBuilder.build())

        Timber.e("request")
        return@runBlocking response
    }


    @SuppressLint("HardwareIds")
    private fun setAuthHeader(builder: Request.Builder, token: String?) {
        if (token != null) {
            builder.addHeader(KEY_AUTH, token)
        }
    }

    companion object {
        val KEY_AUTH = "Authorization"
        var token: String? = null
    }


}