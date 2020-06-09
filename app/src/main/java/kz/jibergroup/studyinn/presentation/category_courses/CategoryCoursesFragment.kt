package kz.jibergroup.studyinn.presentation.category_courses

import android.content.Context
import android.content.Intent
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapp.presentation.global.utils.EventObserver
import kotlinx.android.synthetic.main.category_courses_fragment.*
import kotlinx.android.synthetic.main.error_message_view.*
import kotlinx.android.synthetic.main.error_not_found.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.ItemClickListener
import kz.jibergroup.studyinn.domain.entity.ObjectData
import kz.jibergroup.studyinn.presentation.catalog.CoursesAdapter
import kz.jibergroup.studyinn.presentation.course_detail.CourseDetailActivity
import kz.jibergroup.studyinn.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryCoursesFragment : BaseFragment(), ItemClickListener {

    companion object {
        const val COURSE_CATEGORY_KEY = "COURSE_CATEGORY_KEY"

        fun newInstance(category: ObjectData): CategoryCoursesFragment {
            val fragment = CategoryCoursesFragment()
            val args = Bundle()
            args.putParcelable(COURSE_CATEGORY_KEY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId(): Int {
        return R.layout.category_courses_fragment
    }

    val viewModel: CategoryCoursesViewModel by viewModel()
    lateinit var courseAdapter: CoursesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<ObjectData>(COURSE_CATEGORY_KEY)?.let {
            viewModel.categoryItem.value = it
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCourses()
        viewModel.courses.observe(viewLifecycleOwner, coursesObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errObserver)
        viewModel.initShimmerData()
        viewModel.getCourses()
    }


    private val coursesObserver = Observer<MutableList<CourseItem>> {

        if (it.isNotEmpty()) {
            handleError(HomeViewModel.Error.Success)
            courseAdapter.submitData(it)
        } else {
            handleError(HomeViewModel.Error.Not_found)
        }

    }

    private val errObserver = EventObserver<String> {
        handleError(HomeViewModel.Error.ServerError, it)
    }


    private fun initCourses() {
        courseAdapter = CoursesAdapter()
        courseAdapter.submitListener(this)
        courseAdapter.submitContext(activity as AppCompatActivity)

        categoriesCoursesRecycler.adapter = courseAdapter
        categoriesCoursesRecycler.layoutManager = LinearLayoutManager(activity)

    }


    private fun handleError(error: HomeViewModel.Error, errorLabel: String? = null) {
        when (error) {
            HomeViewModel.Error.Not_found -> {
                categoriesCoursesRecycler.visibility = View.GONE
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.VISIBLE
                error404Description.text = getString(R.string.course_in_this_catagory_not_found)

                error404TryAgain.setOnClickListener {
                    viewModel.initShimmerData()
                    categoriesCoursesRecycler.visibility = View.VISIBLE
                    viewModel.getCourses()
                }
            }
            HomeViewModel.Error.ServerError -> {
                categoriesCoursesRecycler.visibility = View.GONE
                error404Layout.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
                errorDescription.text = errorLabel

                errorTryAgain.setOnClickListener {
                    viewModel.initShimmerData()
                    categoriesCoursesRecycler.visibility = View.VISIBLE
                    viewModel.getCourses()
                }
            }
            HomeViewModel.Error.Success -> {
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.GONE
                categoriesCoursesRecycler.visibility = View.VISIBLE
            }
        }
    }


    override fun click(position: Int, courseItem: CourseItem) {
        openCourseDetail(courseItem)
    }

    private fun openCourseDetail(courseItem: CourseItem) {
        val intent = Intent(activity, CourseDetailActivity::class.java)
        intent.putExtra("courseItem", courseItem)
        activity?.startActivity(intent)
    }


}
