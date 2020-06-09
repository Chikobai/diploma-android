package kz.jibergroup.studyinn.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_login.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.auth.registration.RegisterActivity
import kz.jibergroup.studyinn.presentation.global.base.BaseActivity
import kz.jibergroup.studyinn.presentation.main.MainActivity
import kz.jibergroup.studyinn.presentation.reset_user.ResetUserActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.state.observe(this, stateObserver)
        viewModel.statusLiveData.observe(this, statusObserver)

        initUi()


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


    private fun initUi() {

        registrationTextView.setOnClickListener {
            openRegistration()
        }

        loginButton.setOnClickListener {
            viewModel.onLogin(emailEditText.text.toString(), passwordEditText.text.toString(),this)
        }

        restorePasswordTextView.setOnClickListener {
            openRestoreActivity()
        }

        initInputListener()

    }

    private fun initInputListener() {
        emailEditText.addTextChangedListener {
            emailErrorTextView.visibility = View.GONE
        }
        passwordEditText.addTextChangedListener {
            passwordErrorTextView.visibility = View.GONE
        }
    }

    private fun openRegistration() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun openRestoreActivity() {
        val intent = Intent(this, ResetUserActivity::class.java)
        startActivity(intent)
    }

    private fun openMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
