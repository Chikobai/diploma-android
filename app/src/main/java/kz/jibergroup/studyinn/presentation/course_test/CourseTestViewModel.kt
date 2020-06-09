package kz.jibergroup.studyinn.presentation.course_test

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData

class CourseTestViewModel : BaseViewModel() {

    var quizResult: QuizResult = QuizResult()
    var questions = mutableListOf<Quiz>()
    var squareList = mutableListOf<Int>()

    var quiz_pos: Int = 0
    var user_points: Int = 0
    var user_answers: Int = 0
    var max_points: Int = 0
    var max_answers: Int = 0

    val action = MutableLiveData<SurveyAction>()

    fun getUserAnswer(ans: Answer) {

        if (ans.is_correct) {
            ans.user_answer = 1
            squareList[quiz_pos] = 1

            val points = questions[quiz_pos].point

            points?.let {
                user_points += it
            }

            user_answers = ++user_answers


        } else {

            questions[quiz_pos].answerList?.let {

                for (answer in it) {
                    if (answer.is_correct) {
                        answer.user_answer = 1
                    }
                }
            }

            ans.user_answer = -1
            squareList[quiz_pos] = -1

        }

        if (quiz_pos < questions.size - 1) {
            action.value = SurveyAction.UserAnswerResult
        } else {
            action.value = SurveyAction.FinishResult
        }

    }

    fun getSquareList(questions: MutableList<Quiz>) {
        val data = squareList
        for (i in 0 until questions.size) {
            data.add(0)
        }

        squareList = data


        max_answers = questions.size

        for (quis in questions) {
            quis.point?.let { point ->
                max_points += point
            }
        }

    }


    fun setEndResult() {

        quizResult.max_points = max_points.toString()
        quizResult.user_points = user_points.toString()
        quizResult.max_answers = max_answers.toString()
        quizResult.user_answers = user_answers.toString()

        action.value = SurveyAction.QuizResult
    }

    enum class SurveyAction {
        FinishResult, CompleteTest, QuizResult, UserAnswerResult
    }

}
