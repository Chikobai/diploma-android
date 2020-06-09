package kz.jibergroup.studyinn.presentation.reset_user

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.activity_reset_user.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ObjectDataResetPassword
import kz.jibergroup.studyinn.presentation.global.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetUserActivity : BaseActivity() {


    val viewModel: ResetUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_user)

        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.response.observe(this, responseObserver)

        resetPasswordEmailEditText.addTextChangedListener {
            resetErrorTextView.visibility = View.GONE
        }

        resetButton.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(resetPasswordEmailEditText.text.toString()).matches()) {
                resetErrorTextView.visibility = View.VISIBLE
                resetErrorTextView.text = getString(R.string.invalid_email)
            } else {
                viewModel.submitEmail(resetPasswordEmailEditText.text.toString())
            }
        }
    }

    private val responseObserver = Observer<ObjectDataResetPassword> {
        if (it.success ?: false) {
            alert(
                getString(R.string.attention_alert),
                it.message_ru,
                {
                    finish()
                }, false
            )
        } else {
            alert(getString(R.string.attention_alert), it.message_ru)
        }
    }


}
