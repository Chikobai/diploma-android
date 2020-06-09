package kz.jibergroup.studyinn.presentation.auth.registration

import android.content.Context
import android.ibanking.altyn.presentation.global.base.BaseViewModel
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.AuthItem
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel
import kz.jibergroup.studyinn.presentation.profile.UserLocalNotificationItem

class RegisterViewModel(
    private val personalRepository: PersonalRepository
) : BaseViewModel() {

    var state = MutableLiveData<LoginViewModel.ViewState>()
    var errorMessage = MutableLiveData<LoginViewModel.ErrorMessage>()
    private val minPasswordSize = 8

    fun onRegistration(name: String, lastname: String, password: String, email: String,context: Context) {
        if (isValid(name, lastname, email, password,context)) {
            makeRequest({
                personalRepository.registrationUser(
                    password,
                    name,
                    lastname,
                    email
                )
            }) {
                unwrap(it) { loginResponse ->
                    personalRepository.saveAccessToken(loginResponse.token ?: "")
                    personalRepository.saveNotificationData(UserLocalNotificationItem(false))
                    personalRepository.saveUserLanguage("Русский")
                    state.value = LoginViewModel.ViewState.LoginFinishState
                }
            }
        }

    }

    private fun isValid(
        name: String,
        lastname: String,
        emailValue: String,
        passwordValue: String,
        context: Context
    ): Boolean {
        var valid = true
        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.Email, context.getString(R.string.invalid_email)
            )
        }
        if (passwordValue.length < minPasswordSize) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.Password, context.getString(R.string.invalid_password)
            )
        }
        if (name.isBlank()) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.Name, context.getString(R.string.required_field)
            )
        }
        if (lastname.isBlank()) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.Lastname, context.getString(R.string.required_field)
            )
        }

        return valid
    }


}
