package kz.jibergroup.studyinn.presentation.global.base

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myfirstapp.presentation.global.utils.EventObserver
import com.example.myfirstapp.presentation.global.utils.Status
import com.example.myfirstapp.presentation.global.utils.alert
import kz.jibergroup.studyinn.R


abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: Dialog? = null

    fun showPopupProgress(isShowing: Boolean) {
        if (isShowing) {
            progressDialog = Dialog(this)
            progressDialog?.setContentView(R.layout.progress_layout)
            progressDialog?.setCancelable(false)
            progressDialog?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
        }
    }

    open fun showProgress() {
        showPopupProgress(true)
    }

    open fun hideProgress() {
        showPopupProgress(false)

    }

    open fun success() {}

    protected val statusObserver = Observer<Status> {
        it?.let {
            when (it) {
                Status.SHOW_LOADING -> showProgress()
                Status.HIDE_LOADING -> hideProgress()
                Status.SUCCESS -> success()
            }
        }
    }

    protected val errorMessageObserver = EventObserver<String> {
        alert(message = it)
    }

}
