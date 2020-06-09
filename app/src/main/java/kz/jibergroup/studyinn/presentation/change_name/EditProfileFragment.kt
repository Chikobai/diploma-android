package kz.jibergroup.studyinn.presentation.change_name

import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = EditProfileFragment()
    }

    val viewModel: EditProfileViewModel by viewModel()

    override fun layoutId(): Int {
        return R.layout.edit_profile_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initInputListener()
        viewModel.getUserData()

        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.userData.observe(this, userDataObserver)
        viewModel.response.observe(this, responseObserver)

        ediProfileSaveBtn.setOnClickListener {
            viewModel.submitData(nameEditText.text.toString(), lastNameEditText.text.toString(),activity as AppCompatActivity)
        }

    }


    private val userDataObserver = Observer<ProfileItem> {
        nameEditText.setText(it.first_name)
        lastNameEditText.setText(it.last_name)
    }

    private val responseObserver = Observer<ProfileItem> {
        context?.alert(
            getString(R.string.attention_alert),
            getString(R.string.your_changes_saved),
            {
                navigateBack()
            })
    }

    private fun navigateBack(){
        findNavController().navigateUp()
    }


    private fun initInputListener() {
        nameEditText.addTextChangedListener {
            nameErrorTextView.visibility = View.GONE
        }
        lastNameEditText.addTextChangedListener {
            lastnameErrorTextView.visibility = View.GONE
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

            else -> {
            }
        }
    }

}
