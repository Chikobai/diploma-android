package kz.jibergroup.studyinn.presentation.change_password

import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.activity_register.passwordEditText
import kotlinx.android.synthetic.main.change_password_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ResponseServer
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Appendable

class ChangePasswordFragment : BaseFragment() {

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

    override fun layoutId(): Int {
        return R.layout.change_password_fragment
    }

    val viewModel: ChangePasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initInputListener()

        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.response.observe(this, responseObserver)

        changePasswordButton.setOnClickListener {
            viewModel.submitPassword(
                passwordEditText.text.toString(), passwordNewEditText.text.toString(),
                passwordConfirmEditText.text.toString(),activity as AppCompatActivity)
        }

    }


    private val errorObserver = Observer<LoginViewModel.ErrorMessage> { error ->
        when (error.errorMessageView) {
            LoginViewModel.ErrorMessageView.Password -> {
                passwordErrorTextView.visibility = View.VISIBLE
                passwordErrorTextView.text = error.label
            }
            LoginViewModel.ErrorMessageView.PasswordNew -> {
                passwordNewErrorTextView.visibility = View.VISIBLE
                passwordNewErrorTextView.text = error.label
            }
            LoginViewModel.ErrorMessageView.PasswordConfirm -> {
                passwordConfirmErrorTextView.visibility = View.VISIBLE
                passwordConfirmErrorTextView.text = error.label
            }
            else -> {
            }
        }
    }

    private val responseObserver = Observer<ResponseServer> {
        if (it.success ?: false) {
            context?.alert(
                getString(R.string.attention_alert),
                getString(R.string.your_changes_saved),
                {
                    navigateBack()
                })
        } else {
            context?.alert(getString(R.string.attention_alert), it.message)
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }


    private fun initInputListener() {
        passwordEditText.addTextChangedListener {
            passwordErrorTextView.visibility = View.GONE
        }
        passwordNewEditText.addTextChangedListener {
            passwordNewErrorTextView.visibility = View.GONE
        }
        passwordConfirmEditText.addTextChangedListener {
            passwordConfirmErrorTextView.visibility = View.GONE
        }
    }

}
