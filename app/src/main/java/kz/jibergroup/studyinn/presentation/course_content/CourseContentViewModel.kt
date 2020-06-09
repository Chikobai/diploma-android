package kz.jibergroup.studyinn.presentation.course_content

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.domain.entity.LessonItem

class CourseContentViewModel : BaseViewModel() {

    val lessonItem = MutableLiveData<LessonItem>()


}
