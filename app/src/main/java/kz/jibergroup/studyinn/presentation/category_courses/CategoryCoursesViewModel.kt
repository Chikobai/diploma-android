package kz.jibergroup.studyinn.presentation.category_courses

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.ObjectData

class CategoryCoursesViewModel(private val courseRepository: CourseRepository) : BaseViewModel() {

    val categoryItem = MutableLiveData<ObjectData>()
    val courses = MutableLiveData<MutableList<CourseItem>>()


    fun getCourses() {
        makeRequest({ courseRepository.getCourseCategories(categoryItem.value?.id.toString()) }) {
            unwrap(it) {
                courses.value = it.data
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

        courses.value = data

    }

}
