package kz.jibergroup.studyinn.data.repo

import android.content.Context
import kz.jibergroup.studyinn.data.pref.PrefManager
import kz.jibergroup.studyinn.data.remote.ApiService
import kz.jibergroup.studyinn.domain.ApiCaller
import kz.jibergroup.studyinn.domain.CoroutineCaller
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.presentation.profile.UserLocalNotificationItem
import okhttp3.MultipartBody

class PersonalRepository(
    private val context: Context,
    private val api: ApiService,
    private val prefManager: PrefManager
) : CoroutineCaller by ApiCaller() {

    suspend fun registrationUser(
        password: String,
        first_name: String,
        last_name: String,
        email: String
    ) = coroutineApiCall(api.registrationUser(password, first_name, last_name, email))

    suspend fun changePassword(newPassword: String, oldPassword: String) =
        coroutineApiCall(api.changePassword(oldPassword, newPassword))


    suspend fun authUser(email: String, password: String) =
        coroutineApiCall(api.authUser(email, password))

    suspend fun userLogout() =
        coroutineApiCall(api.logout())

    suspend fun changeEmail(email: String) =
        coroutineApiCall(api.changeEmail(email))

    suspend fun restorePassword(email: String) =
        coroutineApiCall(api.restorePassword(email))

    suspend fun changeImage(image: MultipartBody.Part) =
        coroutineApiCall(api.savePhoto(image))

    suspend fun editProfile(first_name: String, last_name: String) =
        coroutineApiCall(api.editProfile(first_name, last_name))

    fun saveAccessToken(token: String) {
        prefManager.saveAccessToken(token)
    }

    fun getAccessToken(): String {
        return prefManager.getAccessToken()
    }

    fun getUserInfo(): ProfileItem {
        return prefManager.getUserData()
    }

    fun setUserInfo(userInfo: ProfileItem) {
        prefManager.saveUserData(userInfo)
    }

    fun resetUser() {
        prefManager.clean()
    }

    fun getNotificationData(): UserLocalNotificationItem {
        return prefManager.getLocalNotificationData()
    }

    fun saveNotificationData(userLocalNotificationItem: UserLocalNotificationItem) {
        prefManager.saveLocalNotificationData(userLocalNotificationItem)
    }

    fun getUserLanguage(): String {
        return prefManager.getLanguage()
    }

    fun saveUserLanguage(language: String) {
        prefManager.saveUserLanguage(language)
    }



}