package kz.jibergroup.studyinn.presentation.change_email

import android.content.Context
import android.ibanking.altyn.presentation.global.base.BaseViewModel
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.domain.entity.ResponseServer
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel

class ChangeEmailViewModel(private val personalRepository: PersonalRepository) : BaseViewModel() {

    var errorMessage = MutableLiveData<LoginViewModel.ErrorMessage>()
    val response = MutableLiveData<ResponseServer>()
    val userData = MutableLiveData<ProfileItem>()

    fun getUserData() {
        userData.value = personalRepository.getUserInfo()
    }

    fun submitEmail(emailValue: String, newEmailValue: String,context: Context) {
        if (isValid(emailValue, newEmailValue,context)) {
            makeRequest({
                personalRepository.changeEmail(
                    newEmailValue
                )
            }) {
                unwrap(it) {
                    response.value = it
                }
            }
        }
    }

    private fun isValid(emailValue: String, newEmailValue: String,context: Context): Boolean {
        var valid = true
        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.Email, context.getString(R.string.invalid_email)
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmailValue).matches()) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.EmailNew, context.getString(R.string.invalid_email)
            )
        }


        return valid
    }


}
