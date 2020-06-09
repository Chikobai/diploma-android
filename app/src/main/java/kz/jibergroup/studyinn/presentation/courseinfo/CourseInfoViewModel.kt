package kz.jibergroup.studyinn.presentation.courseinfo

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.CourseSkill
import kz.jibergroup.studyinn.domain.entity.Owner

class CourseInfoViewModel : BaseViewModel() {

    val courseItem = MutableLiveData<CourseItem>()
    val courseSkills = MutableLiveData<MutableList<CourseSkill>>()
    val courseTeachers = MutableLiveData<MutableList<Owner>>()

    fun initCourse(course: CourseItem) {
        courseItem.value = course
        initTeacher()
        initSkills()
    }

    private fun initTeacher() {

        val teachers = mutableListOf<Owner>()

        courseItem.value?.owner?.let {
            teachers.add(it)
        }

        courseTeachers.value = teachers

    }

    private fun initSkills() {

        val skills = mutableListOf<CourseSkill>()

        courseItem.value?.course_skills?.forEach {
            skills.add(it)
        }

        courseSkills.value = skills

    }


}
