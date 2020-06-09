package kz.jibergroup.studyinn.presentation.auth.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_register.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.auth.login.LoginActivity
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel
import kz.jibergroup.studyinn.presentation.global.base.BaseActivity
import kz.jibergroup.studyinn.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.state.observe(this, stateObserver)

        initUi()
    }

    private fun initUi() {
        registrationButton.setOnClickListener {
            viewModel.onRegistration(
                nameEditText.text.toString(),
                lastNameEditText.text.toString(),
                passwordEditText.text.toString(),
                EmailEditText.text.toString(),this
            )
        }

        registrationLoginTextView.setOnClickListener {
            openLoginPage()
        }
        initInputListener()
    }


    private fun initInputListener() {
        nameEditText.addTextChangedListener {
            nameErrorTextView.visibility = View.GONE
        }
        lastNameEditText.addTextChangedListener {
            lastnameErrorTextView.visibility = View.GONE
        }
        EmailEditText.addTextChangedListener {
            emailErrorTextView.visibility = View.GONE
        }
        passwordEditText.addTextChangedListener {
            passwordErrorTextView.visibility = View.GONE
        }
    }

    private val stateObserver = Observer<LoginViewModel.ViewState> { state ->
        when (state) {
            LoginViewModel.ViewState.LoginFinishState -> {
                openMainPage()
            }
            else -> {
            }
        }
    }

    private val errorObserver = Observer<LoginViewModel.ErrorMessage> { error ->
        when (error.errorMessageView) {
            LoginViewModel.ErrorMessageView.Name -> {
                nameErrorTextView.visibility = View.VISIBLE
                nameErrorTextView.text = error.label
            }
            LoginViewModel.ErrorMessageView.Lastname -> {
                lastnameErrorTextView.visibility = View.VISIBLE
                lastnameErrorTextView.text = error.label
            }
            LoginViewModel.ErrorMessageView.Email -> {
                emailErrorTextView.visibility = View.VISIBLE
                emailErrorTextView.text = error.label
            }
            LoginViewModel.ErrorMessageView.Password -> {
                passwordErrorTextView.visibility = View.VISIBLE
                passwordErrorTextView.text = error.label
            }
            else -> {
            }
        }
    }


    private fun openLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
