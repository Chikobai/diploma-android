package kz.jibergroup.studyinn.presentation.my_courses

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.DataInitType

class MyCoursesViewModel(
    private val courseRepository: CourseRepository
) : BaseViewModel() {
    val myCourses = MutableLiveData<MutableList<CourseItem>>()

    fun getMyCourses() {
        makeRequest({ courseRepository.getAllMyCourses() }) {
            unwrap(it) {
                myCourses.value = it.data
            }
        }

    }

    fun initShimmerData() {
        val data = mutableListOf<CourseItem>()
        data.add(CourseItem(dataInitType = DataInitType.SHIMMER))
        data.add(CourseItem(dataInitType = DataInitType.SHIMMER))
        data.add(CourseItem(dataInitType = DataInitType.SHIMMER))
        data.add(CourseItem(dataInitType = DataInitType.SHIMMER))
        data.add(CourseItem(dataInitType = DataInitType.SHIMMER))

        myCourses.value = data

    }


}
