package kz.jibergroup.studyinn.presentation.modules

import android.content.Context
import android.content.Intent
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.course_modules_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.presentation.course_learning.CourseLearningActivity
import kz.jibergroup.studyinn.presentation.course_learning.LESSON_ID_KEY
import kz.jibergroup.studyinn.presentation.course_learning.MODULE_ID_KEY
import kz.jibergroup.studyinn.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseModulesFragment : BaseFragment() {

    companion object {
        private const val courseItemKey = "Course_Item"

        fun newInstance(courseItem: CourseItem): CourseModulesFragment {
            val fragment = CourseModulesFragment()
            val args = Bundle()
            args.putParcelable(courseItemKey, courseItem)
            fragment.arguments = args
            return fragment
        }
    }

    private var courseId = 0
    val viewModel: CourseModulesViewModel by viewModel()

    override fun layoutId(): Int {
        return R.layout.course_modules_fragment
    }

    lateinit var adapter: CourseModulesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<CourseItem>(courseItemKey)?.let {
            courseId = it.id ?: -1
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.modules.observe(viewLifecycleOwner, modulesObserver)
        viewModel.getModules(courseId)
    }

    private val modulesObserver = Observer<MutableList<ModuleItem>> {
        if (it.isEmpty()) {
            handleError(HomeViewModel.Error.Not_found)
        } else {
            handleError(HomeViewModel.Error.Success)
            initModules()
        }
    }

    private fun initModules() {

        adapter = CourseModulesAdapter(viewModel.modules.value ?: mutableListOf(),
            onItemClickListener = { moduleId, lessonId ->
                openCourseLesson(
                    moduleId ?: -1,
                    lessonId ?: -1
                )
            })

        courseModuleRecycler.adapter = adapter
        courseModuleRecycler.layoutManager = LinearLayoutManager(activity)

    }

    private fun openCourseLesson(moduleId: Int, lessonId: Int) {
        val intent = Intent(requireActivity(), CourseLearningActivity::class.java)
        intent.putExtra(MODULE_ID_KEY, moduleId)
        intent.putExtra(LESSON_ID_KEY, lessonId)
        startActivity(intent)
    }

    private fun handleError(error: HomeViewModel.Error, errorLabel: String? = null) {
        when (error) {
            HomeViewModel.Error.Not_found -> {
                courseModuleRecycler.visibility = View.GONE
                progressBar.visibility = View.GONE
                courseModuleNotFound.visibility = View.VISIBLE
            }
            HomeViewModel.Error.Success -> {
                progressBar.visibility = View.GONE
                courseModuleNotFound.visibility = View.GONE
                courseModuleRecycler.visibility = View.VISIBLE
            }
            else -> {
            }
        }
    }

}

