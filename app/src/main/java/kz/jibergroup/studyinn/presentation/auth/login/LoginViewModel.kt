package kz.jibergroup.studyinn.presentation.auth.login

import android.content.Context
import android.ibanking.altyn.presentation.global.base.BaseViewModel
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.presentation.profile.UserLocalNotificationItem

class LoginViewModel(
    private val personalRepository: PersonalRepository
) : BaseViewModel() {

    var state = MutableLiveData<ViewState>()
    var errorMessage = MutableLiveData<ErrorMessage>()
    private val minPasswordSize = 8

    fun onLogin(email: String, password: String,context: Context) {
        if (isValid(email, password,context)) {
            makeRequest({ personalRepository.authUser(email, password) }) { result ->
                unwrap(result) { loginResponse ->
                    personalRepository.saveAccessToken(loginResponse.token ?: "")
                    loginResponse.profile?.let {
                        personalRepository.setUserInfo(it)
                    }
                    personalRepository.saveUserLanguage("Русский")
                    personalRepository.saveNotificationData(UserLocalNotificationItem(false))
                    state.value = ViewState.LoginFinishState
                }
            }

        }

    }

    private fun isValid(emailValue: String, passwordValue: String,context: Context): Boolean {
        var valid = true
        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            valid = false
            errorMessage.value = ErrorMessage(
                ErrorMessageView.Email, context.getString(R.string.invalid_email)
            )
        }
        if (passwordValue.length < minPasswordSize) {
            valid = false
            errorMessage.value = ErrorMessage(
                ErrorMessageView.Password, context.getString(R.string.invalid_password)
            )
        }

        return valid
    }

    enum class ViewState {
        LoadingState, NormalState, LoginFinishState
    }

    enum class ErrorMessageView {
        Email, Password, Name, Lastname,PasswordNew,PasswordConfirm,EmailNew
    }

    data class ErrorMessage(val errorMessageView: ErrorMessageView, val label: String)


}