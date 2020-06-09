package kz.jibergroup.studyinn.presentation.settings

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SettingsViewModel(private val personalRepository: PersonalRepository) : BaseViewModel() {

    val userLogState = MutableLiveData<LogOutState>()
    val image = MutableLiveData<String>()
    val userData = MutableLiveData<ProfileItem>()
    val userLang = MutableLiveData<String>()


    fun getLanguage(): String {
        userLang.value = personalRepository.getUserLanguage()
        val lang = personalRepository.getUserLanguage()
        return lang
    }

    fun saveLanguage(language: String) {
        userLang.value = language
        personalRepository.saveUserLanguage(language)
    }


    fun savePhoto() {
        image.value?.let {
            makeRequest({ personalRepository.changeImage(returnImageFormData(it)) }) {
                unwrap(it) {
                    personalRepository.setUserInfo(it as ProfileItem)
                    getUserData()
                }
            }
        }
    }

    fun getUserData() {
        userData.value = personalRepository.getUserInfo()
    }

    fun onLogoutUser() {
        userLogState.value = LogOutState.CONFIRM
    }

    fun cleanStorage() {
        makeRequest({ personalRepository.userLogout() }) { result ->
            unwrap(result) {
                personalRepository.resetUser()
                userLogState.value = LogOutState.FINISHED
            }
        }
    }

    fun returnImageFormData(image: String): MultipartBody.Part {

        val file = File(image)
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        // Create MultipartBody.Part using file request-body,file name and part name
        val part = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody)

        return part
    }

}

enum class LogOutState {
    CONFIRM, CONFIRMED, FINISHED
}

