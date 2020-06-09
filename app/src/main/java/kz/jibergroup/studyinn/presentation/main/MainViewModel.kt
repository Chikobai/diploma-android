package kz.jibergroup.studyinn.presentation.main

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.PersonalRepository

class MainViewModel(private val personalRepository: PersonalRepository) : BaseViewModel() {


    val userState = MutableLiveData<UserState>()


    fun checkUserAuthority() {
        if (personalRepository.getAccessToken().isNotBlank()) {
            userState.value = UserState.AUTHORIZED
        } else {
            userState.value = UserState.UNAUTHORIZED
        }
    }


    enum class UserState {
        AUTHORIZED, UNAUTHORIZED
    }


}