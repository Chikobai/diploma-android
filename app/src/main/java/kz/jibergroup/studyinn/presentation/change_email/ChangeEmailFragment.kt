package kz.jibergroup.studyinn.presentation.change_email

import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.change_email_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.domain.entity.ResponseServer
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeEmailFragment : BaseFragment() {

    companion object {
        fun newInstance() = ChangeEmailFragment()
    }

    override fun layoutId(): Int {
        return R.layout.change_email_fragment
    }


    val viewModel: ChangeEmailViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInputListener()

        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.response.observe(this, responseObserver)
        viewModel.userData.observe(this, userDataObserver)

        viewModel.getUserData()

        changeEmailBtn.setOnClickListener {
            viewModel.submitEmail(emailEditText.text.toString(), newEmailEditText.text.toString(),activity as AppCompatActivity)
        }


    }

    private val userDataObserver = Observer<ProfileItem> {
        emailEditText.setText(it.email)
    }

    private val errorObserver = Observer<LoginViewModel.ErrorMessage> { error ->
        when (error.errorMessageView) {
            LoginViewModel.ErrorMessageView.Email -> {
                changeEmailErrorTextView.visibility = View.VISIBLE
                changeEmailErrorTextView.text = error.label
            }
            LoginViewModel.ErrorMessageView.EmailNew -> {
                changeNewEmailErrorTextView.visibility = View.VISIBLE
                changeNewEmailErrorTextView.text = error.label
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

    private fun navigateBack(){
        findNavController().navigateUp()
    }


    private fun initInputListener() {
        emailEditText.addTextChangedListener {
            changeEmailErrorTextView.visibility = View.GONE
        }
        newEmailEditText.addTextChangedListener {
            changeNewEmailErrorTextView.visibility = View.GONE
        }
    }

}
