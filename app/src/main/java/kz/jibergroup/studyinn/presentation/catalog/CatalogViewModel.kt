package kz.jibergroup.studyinn.presentation.catalog

import android.content.Context
import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.domain.entity.ObjectData
import kz.jibergroup.studyinn.presentation.category_courses.CategoryCoursesFragment

class CatalogViewModel(
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    val categories = MutableLiveData<MutableList<ObjectData>>()
    val categoryTitles = MutableLiveData<MutableList<String>>()
    val categoryFragments = MutableLiveData<MutableList<Fragment>>()


    fun getCourseCategories() {
        makeRequest({ courseRepository.getCourseCategories() }) {
            unwrap(it) {
                categories.value = it
            }
        }
    }

    fun mapCategoryTitle() {

        val data = mutableListOf<String>()

        categories.value?.forEach {
            data.add(it.name_ru ?: "")
        }

        categoryTitles.value = data

    }

    fun mapCategoryFragments() {

        val data = mutableListOf<Fragment>()
        categories.value?.forEach {
            data.add(CategoryCoursesFragment.newInstance(it))
        }

        categoryFragments.value = data

    }


}
