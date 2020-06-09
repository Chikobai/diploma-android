package kz.jibergroup.studyinn.presentation.course_review

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.course_review_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.ReviewItem
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import kz.jibergroup.studyinn.presentation.review_dialog.ReviewDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseReviewFragment : BaseFragment() {

    companion object {
        private const val courseItemKey = "Course_Item"

        fun newInstance(courseItem: CourseItem): CourseReviewFragment {
            val fragment = CourseReviewFragment()
            val args = Bundle()
            args.putParcelable(courseItemKey, courseItem)
            fragment.arguments = args
            return fragment
        }
    }

    private var courseId = 0
    val adapter = ReviewAdapter()
    val viewModel: CourseReviewViewModel by viewModel()
    var dataSet = mutableListOf<ReviewItem>()

    override fun layoutId(): Int {
        return R.layout.course_review_fragment
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<CourseItem>(courseItemKey)?.let {
            courseId = it.id ?: -1
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initReview()
        viewModel.initShimmerData()

        viewModel.reviews.observe(viewLifecycleOwner, reviewsObserver)
        viewModel.getReviews(courseId)
        courseReviewWrite.setOnClickListener {
            openReviewDialog(courseId)
        }

        courseReviewRefresh.setOnRefreshListener {
            viewModel.initShimmerData()
            viewModel.getReviews(courseId)
        }

    }

    private val reviewsObserver = Observer<MutableList<ReviewItem>> {
        courseReviewRefresh.isRefreshing = false
        adapter.submitData(it)
    }

    private fun initReview() {
        courseReviewRecycler.layoutManager = LinearLayoutManager(activity)
        courseReviewRecycler.adapter = adapter
    }

    private fun openReviewDialog(id: Int) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack(null)
        val dialogFragment = ReviewDialogFragment.newInstance(id)
        fragmentTransaction?.let {
            dialogFragment.show(it, "dialog")
        }
    }
}
