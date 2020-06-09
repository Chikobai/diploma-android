package kz.jibergroup.studyinn.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.presentation.profile.UserLocalNotificationItem

class PrefManager(private val context: Context) {

    private val DEFAULT_VALUE = ""

    companion object {
        const val PREF_NAME = "PREF_NAME"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val USER_DATA = "USER_DATA"
        const val USER_LOCAL_NOTIFICATION = "USER_LOCAL_NOTIFICATION"
        const val LANGUAGE = "LANGUAGE"

    }

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun clean() {
        sharedPreferences.edit().clear().apply()
    }


    fun saveAccessToken(token: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, DEFAULT_VALUE) ?: ""
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit()
            .putString(REFRESH_TOKEN, token).apply()
    }

    fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, DEFAULT_VALUE) ?: ""
    }

    fun saveUserData(userInfo: ProfileItem) {
        val gson = Gson()
        val data = gson.toJson(userInfo)

        sharedPreferences.edit()
            .putString(USER_DATA, data).apply()
    }

    fun getUserData() : ProfileItem {
        val gson = Gson()
        val json = sharedPreferences.getString(USER_DATA, DEFAULT_VALUE)
        val data = gson.fromJson<Any>(json, ProfileItem::class.java) as ProfileItem
        return data
    }

    fun getLocalNotificationData() : UserLocalNotificationItem {
        val gson = Gson()
        val json = sharedPreferences.getString(USER_LOCAL_NOTIFICATION, DEFAULT_VALUE)
        val data = gson.fromJson<Any>(json, UserLocalNotificationItem::class.java) as UserLocalNotificationItem
        return data
    }

    fun saveLocalNotificationData(userLocalNotificationItem: UserLocalNotificationItem) {
        val gson = Gson()
        val data = gson.toJson(userLocalNotificationItem)

        sharedPreferences.edit()
            .putString(USER_LOCAL_NOTIFICATION, data).apply()
    }

    fun getLanguage(): String {
        return sharedPreferences.getString(LANGUAGE, DEFAULT_VALUE) ?: ""
    }

    fun saveUserLanguage(language: String) {
        sharedPreferences.edit()
            .putString(LANGUAGE, language).apply()
    }


}
