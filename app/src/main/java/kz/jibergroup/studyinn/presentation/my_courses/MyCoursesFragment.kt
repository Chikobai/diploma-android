package kz.jibergroup.studyinn.presentation.my_courses

import android.content.Intent
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapp.presentation.global.utils.EventObserver
import kotlinx.android.synthetic.main.error_message_view.*
import kotlinx.android.synthetic.main.error_not_found.*
import kotlinx.android.synthetic.main.my_courses_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.ItemClickListener
import kz.jibergroup.studyinn.presentation.course_detail.CourseDetailActivity
import kz.jibergroup.studyinn.presentation.course_learning.CourseLearningActivity
import kz.jibergroup.studyinn.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyCoursesFragment : BaseFragment(), ItemClickListener {

    companion object {
        fun newInstance() = MyCoursesFragment()
    }

    override fun layoutId(): Int {
        return R.layout.my_courses_fragment
    }

    val viewModel: MyCoursesViewModel by viewModel()

    var dataset: MutableList<CourseItem> = mutableListOf()

    val adapter = MyCoursesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initMyCourses()

        viewModel.myCourses.observe(viewLifecycleOwner, myCoursesObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errObserver)
        viewModel.initShimmerData()
        viewModel.getMyCourses()
    }

    private val myCoursesObserver = Observer<MutableList<CourseItem>> {
        if (it.isNotEmpty()) {
            handleError(HomeViewModel.Error.Success)
            adapter.submitData(it)
            adapter.submitContext(activity as AppCompatActivity)
        } else {
            handleError(HomeViewModel.Error.Not_found)
            error404TryAgain.setOnClickListener {
                myCourseRecycler.visibility = View.VISIBLE
                viewModel.getMyCourses()
            }
        }

    }

    override fun click(position: Int, courseItem: CourseItem) {
//        onOpenLearningCourse(courseItem)
        openCourseDetail(courseItem)
    }

    private fun onOpenLearningCourse(courseItem: CourseItem) {
        val intent = Intent(activity, CourseLearningActivity::class.java)
        intent.putExtra("courseItem", courseItem)
        activity?.startActivity(intent)
    }

    private fun openCourseDetail(courseItem: CourseItem) {
        val intent = Intent(activity, CourseDetailActivity::class.java)
        intent.putExtra("courseItem", courseItem)
        activity?.startActivity(intent)
    }

    private fun initMyCourses() {

        adapter.submitListener(this)
        myCourseRecycler.adapter = adapter
        myCourseRecycler.layoutManager = LinearLayoutManager(activity)
    }

    private val errObserver = EventObserver<String> {
        handleError(HomeViewModel.Error.ServerError, it)

        errorTryAgain.setOnClickListener {
            myCourseRecycler.visibility = View.VISIBLE
            viewModel.getMyCourses()
        }
    }

    private fun handleError(error: HomeViewModel.Error, errorLabel: String? = null) {
        when (error) {
            HomeViewModel.Error.Not_found -> {
                myCourseRecycler.visibility = View.GONE
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.VISIBLE
                error404Description.text = getString(R.string.user_courses_not_found)
            }
            HomeViewModel.Error.ServerError -> {
                myCourseRecycler.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
                errorDescription.text = errorLabel
            }
            HomeViewModel.Error.Success -> {
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.GONE
                myCourseRecycler.visibility = View.VISIBLE
            }
        }
    }

}
