package kz.jibergroup.studyinn.presentation.change_password

import android.content.Context
import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.ResponseServer
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel

class ChangePasswordViewModel(private val personalRepository: PersonalRepository) :
    BaseViewModel() {


    var errorMessage = MutableLiveData<LoginViewModel.ErrorMessage>()
    val response = MutableLiveData<ResponseServer>()
    private val minPasswordSize = 8


    fun submitPassword(current: String, new: String, newConfirm: String,context: Context) {
        if (isValid(current, new, newConfirm,context)) {
            makeRequest({
                personalRepository.changePassword(
                    new, current
                )
            }) {
                unwrap(it) {
                    response.value = it
                }
            }
        }
    }


    fun isValid(current: String, new: String, newConfirm: String,context: Context): Boolean {
        var valid = true


        if (!new.equals(newConfirm)) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.PasswordConfirm, context.getString(R.string.passwords_are_different)
            )
        }

        if (current.length < minPasswordSize) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.Password,
                context.getString(R.string.invalid_password)
            )
        }

        if (new.length < minPasswordSize) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.PasswordNew,
                context.getString(R.string.invalid_password)
            )
        }

        if (newConfirm.length < minPasswordSize) {
            valid = false
            errorMessage.value = LoginViewModel.ErrorMessage(
                LoginViewModel.ErrorMessageView.PasswordConfirm,
                context.getString(R.string.invalid_password)
            )
        }



        return valid
    }


    enum class ErrorView {
        Password, NewPassword, ConfirmPassword
    }

}
