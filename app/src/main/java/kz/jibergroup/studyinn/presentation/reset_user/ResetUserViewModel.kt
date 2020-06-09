package kz.jibergroup.studyinn.presentation.reset_user

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.ObjectDataResetPassword

class ResetUserViewModel(private val personalRepository: PersonalRepository) : BaseViewModel() {

    val response = MutableLiveData<ObjectDataResetPassword>()

    fun submitEmail(emailValue: String) {
        makeRequest({
            personalRepository.restorePassword(
                emailValue
            )
        }) {
            unwrap(it) {
                response.value = it
            }
        }
    }


}