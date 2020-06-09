package kz.jibergroup.studyinn.presentation.change_name

import android.content.Context
import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel

class EditProfileViewModel(private val personalRepository: PersonalRepository) : BaseViewModel() {

    var errorMessage = MutableLiveData<LoginViewModel.ErrorMessage>()
    val userData = MutableLiveData<ProfileItem>()
    val response = MutableLiveData<ProfileItem>()


    fun getUserData() {
        userData.value = personalRepository.getUserInfo()
    }


    fun submitData(name: String, lastname: String,context: Context) {
        if (isValid(name, lastname,context)) {
            makeRequest({
                personalRepository.editProfile(
                    name,
                    lastname
                )
            }) {
                unwrap(it) {
                    personalRepository.setUserInfo(it)
                    response.value = it
                }
            }
        }

    }


    private fun isValid(
        name: String,
        lastname: String,
        context: Context
    ): Boolean {
        var valid = true
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
