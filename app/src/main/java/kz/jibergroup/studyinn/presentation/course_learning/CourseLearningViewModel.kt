package kz.jibergroup.studyinn.presentation.course_learning

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.domain.entity.AnswerItem
import kz.jibergroup.studyinn.domain.entity.LessonItem
import kz.jibergroup.studyinn.domain.entity.PageItem
import kz.jibergroup.studyinn.presentation.course_content.CourseContentFragment
import kz.jibergroup.studyinn.presentation.course_test.Answer
import kz.jibergroup.studyinn.presentation.course_test.CourseTestFragment
import kz.jibergroup.studyinn.presentation.course_test.Quiz
import kz.jibergroup.studyinn.presentation.course_video.CourseVideoFragment

class CourseLearningViewModel(
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    val moduleId = MutableLiveData<Int>()
    val lessonId = MutableLiveData<Int>()
    val lesson = MutableLiveData<LessonItem>()
    var state = MutableLiveData<ViewMessage>()
    var fragmentItems = MutableLiveData<MutableList<FragmentItem>>()

    val survey = MutableLiveData<MutableList<Quiz>>()


    fun getCourseLessonPages(module_id: Int, lesson_id: Int) {
        state.value = ViewMessage(ViewState.Loading)
        makeRequest({ courseRepository.getCourseLessonPages(module_id, lesson_id) }) {
            state.value = ViewMessage(ViewState.Normal)
            unwrap(it) {
                lesson.value = it
                if (it.pages?.isNotEmpty() ?: false) {
                    state.value = ViewMessage(ViewState.Data)
                } else {
                    state.value = ViewMessage(ViewState.Notfound)
                }
            }
        }

    }

    fun initLessonPages() {

        val data = mutableListOf<FragmentItem>()
        val answerItems = arrayListOf<AnswerItem>()
        val questionItems = arrayListOf<PageItem>()

        lesson.value?.let {
            data.add(
                FragmentItem(
                    CourseContentFragment.newInstance(lessonItem = it),
                    LessonType.Content
                )
            )
        }

        lesson.value?.pages?.forEachIndexed { index, pageItem ->
            when (pageItem.type) {
                0 -> {
                    lesson.value?.let {
                        data.add(
                            FragmentItem(
                                CourseContentFragment.newInstance(lessonItem = it),
                                LessonType.Content
                            )
                        )
                    }
                }
                1 -> {
                    data.add(
                        FragmentItem(
                            CourseVideoFragment.newInstance(pageItem.label + "   " + pageItem.id.toString()),
                            LessonType.Video
                        )
                    )
                }
                2 -> {
                    questionItems.add(pageItem)
                }
            }
        }

        if (questionItems.isNotEmpty()) {
            data.add(
                FragmentItem(
                    CourseTestFragment.newInstance(serverToLocalSurveyJsonConverter(questionItems)),
                    LessonType.Survey
                )
            )
        }

        fragmentItems.value = data

    }


    private fun serverToLocalSurveyJsonConverter(pageItems: MutableList<PageItem>): ArrayList<Quiz> {
        val quiz = arrayListOf<Quiz>()

        pageItems.forEach {

            val answers = mutableListOf<Answer>()
            it.answers?.forEach {
                answers.add(Answer(id = it.id, title = it.text, is_correct = it.is_true ?: false))
            }

            quiz.add(Quiz(id = it.id, question = it.label, point = 1, answerList = answers))
        }


        return quiz
    }

    enum class ViewState {
        Loading, Normal, Data, Notfound, Error
    }


    data class ViewMessage(val errorMessageView: ViewState, val label: String? = null)


    enum class LessonType {
        Content, Video, Survey
    }

    data class FragmentItem(val fragment: Fragment, val lessonType: LessonType)

}