package kz.jibergroup.studyinn.presentation.review_dialog

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.review_dialog_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.domain.entity.ReviewSubmitResponse
import kz.jibergroup.studyinn.presentation.global.base.BaseDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewDialogFragment : BaseDialog() {

    companion object {
        const val COURSE_ID = "COURSE_ID"

        fun newInstance(position: Int): ReviewDialogFragment {
            val fragment = ReviewDialogFragment()
            val args = Bundle()
            args.putInt(COURSE_ID, position)
            fragment.arguments = args
            return fragment
        }

    }

    override fun layoutId(): Int {
        return R.layout.review_dialog_fragment
    }

    val viewModel: ReviewDialogViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getInt(COURSE_ID)?.let {
            viewModel.courseId.value = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.userData.observe(this, userDatObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.response.observe(this, responseObserver)
        viewModel.getUserData()

        reviewRemove.setOnClickListener {
            dialog?.dismiss()
        }

        reviewSubmit.setOnClickListener {
            if (reviewText.text.toString().isNotBlank()) {
                viewModel.submitReview(reviewRatingStars.rating, reviewText.text.toString())
            } else {
                reviewTextError.visibility = View.VISIBLE
            }
        }

        reviewText.addTextChangedListener {
            reviewTextError.visibility = View.GONE
        }

    }

    private val userDatObserver = Observer<ProfileItem> {
        reviewUsername.text = it.first_name.plus(" " + it.last_name)
        Glide.with(activity as AppCompatActivity).load(it.image.toString())
            .into(reviewImage)
    }

    private val responseObserver = Observer<ReviewSubmitResponse> {
        context?.alert(
            getString(R.string.attention_alert), getString(R.string.your_review_submitted),
            {
                dialog?.dismiss()
            }, false
        )
    }

    override fun onStart() {
        super.onStart()

        val displayMetrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels * 0.95
        val width = displayMetrics.widthPixels * 0.98
        getDialog()?.getWindow()?.setLayout(width.toInt(), height.toInt())


    }


}
