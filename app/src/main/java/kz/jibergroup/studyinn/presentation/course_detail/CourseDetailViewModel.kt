package kz.jibergroup.studyinn.presentation.course_detail

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.GeneralResponse
import kz.jibergroup.studyinn.presentation.course_review.CourseReviewFragment
import kz.jibergroup.studyinn.presentation.courseinfo.CourseInfoFragment
import kz.jibergroup.studyinn.presentation.modules.CourseModulesFragment

class CourseDetailViewModel(
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    var result = MutableLiveData<GeneralResponse>()

    val courseItem = MutableLiveData<CourseItem>()
    val courseFragments = MutableLiveData<MutableList<Fragment>>()

    fun joinCourse(id: Int) {
        makeRequest({ courseRepository.joinCourseToMyCourse(id) }) {
            unwrap(it) {
                result.value = it
            }
        }
    }

    fun initCourseFragments() {
        val data = mutableListOf<Fragment>()

        courseItem.value?.let {
            data.add(CourseInfoFragment.newInstance(it))
            data.add(CourseReviewFragment.newInstance(it))
            data.add(CourseModulesFragment.newInstance(it))
        }

        courseFragments.value = data
    }
}