package kz.jibergroup.studyinn.presentation.global.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myfirstapp.presentation.global.utils.EventObserver
import com.example.myfirstapp.presentation.global.utils.Status
import com.example.myfirstapp.presentation.global.utils.alert
import kz.jibergroup.studyinn.R

abstract class BaseDialog: DialogFragment() {

    abstract fun layoutId(): Int

    private var progressDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    fun showPopupProgress(isShowing: Boolean) {
        if (isShowing) {
            progressDialog = Dialog(activity as AppCompatActivity)
            progressDialog?.setContentView(R.layout.progress_layout)
            progressDialog?.setCancelable(false)
            progressDialog?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
        }
    }


    /**
     * Answer
     * https://medium.com/@elye.project/handling-illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-d4ee8b630066
     * https://stackoverflow.com/questions/7575921/illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-wit?source=post_page-----d4ee8b630066----------------------
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        //No call for super(). Bug on API Level > 11.
    }

    open fun showProgress() {
        showPopupProgress(true)
    }

    open fun hideProgress() {
        showPopupProgress(false)
    }

    open fun success() {
        // do nothing
    }

    open fun onError(message: String) {
        // do nothing
    }

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
        context?.alert(message = it)
        onError(it)
    }
}

