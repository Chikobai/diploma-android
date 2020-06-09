package kz.jibergroup.studyinn.presentation.course_test

import android.content.Context
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.course_test_fragment.*
import kz.jibergroup.studyinn.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseTestFragment : BaseFragment() {

    companion object {
        private const val courseItemKey = "Course_Item"

        fun newInstance(answerList: ArrayList<Quiz>): CourseTestFragment {
            val fragment = CourseTestFragment()
            val args = Bundle()
            args.putParcelableArrayList(courseItemKey, answerList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId(): Int {
        return R.layout.course_test_fragment
    }

    var answersAdapter = AnswersAdapter()
    var squareAdapter = SquareGridAdapter()
    val viewModel: CourseTestViewModel by viewModel()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelableArrayList<Quiz>(courseItemKey)?.let {
            viewModel.questions = it.toMutableList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        viewModel.questions = getQuiz()

        viewModel.getSquareList(viewModel.questions)
        viewModel.action.observe(viewLifecycleOwner, actionObserver)

        initQuestions()
        setSquareImage(viewModel.squareList)
        getQuizPosition(
            viewModel.quiz_pos,
            viewModel.questions
        )

    }

    private val actionObserver = Observer<CourseTestViewModel.SurveyAction> {
        when (it) {
            CourseTestViewModel.SurveyAction.FinishResult -> {
                getFinishResult()
            }
            CourseTestViewModel.SurveyAction.CompleteTest -> {
                onCompleteTest()
            }
            CourseTestViewModel.SurveyAction.QuizResult -> {
                getEndResultData(viewModel.quizResult)
            }
            CourseTestViewModel.SurveyAction.UserAnswerResult -> {
                getUserAnswerResult(++viewModel.quiz_pos, viewModel.questions)
            }
            else -> {
            }
        }
    }

    private fun initQuestions() {
        recyclerAnswersAll.layoutManager = LinearLayoutManager(activity)
        answersAdapter.addContext(activity as AppCompatActivity)
        answersAdapter.setOnClickCheckBox(itemOnClickListener)
        recyclerAnswersAll.adapter = answersAdapter
        recyclerAnswersAll.isNestedScrollingEnabled = false
    }


    private fun getQuizPosition(position: Int, quizList: MutableList<Quiz>) {

        txtQuizTextAll.text = quizList[position].question
        squareAdapter.setCurrentPos(position)
        txtCurrentQuizPosAll.text = ("${(position + 1)} / ${quizList.size}")
        answersAdapter.setDataList(quizList[position].answerList)

    }

    private val itemOnClickListener = CompoundButton.OnCheckedChangeListener { view, isChecked ->
        if (isChecked) {
            val ans = view.tag as Answer

            viewModel.getUserAnswer(ans)
        }
    }

    fun getUserAnswerResult(position: Int, quizList: MutableList<Quiz>) {

        val handler2 = Handler()
        handler2.postDelayed({
            startAnimation()
        }, 2000)


        val handler = Handler()
        handler.postDelayed({
            answersAdapter.setIsEnabled(true)
            getQuizPosition(position, quizList)
            endAnimation()
        }, 3000)

        answersAdapter.setIsEnabled(false)
        answersAdapter.notifyDataSetChanged()
        squareAdapter.notifyDataSetChanged()
    }

    fun getFinishResult() {

        val handler2 = Handler()
        handler2.postDelayed({
        }, 2000)

        val handler = Handler()
        handler.postDelayed({
            answersAdapter.setIsEnabled(true)
            squareAdapter.setCurrentPos(100)
            viewModel.setEndResult()
        }, 3000)

        answersAdapter.setIsEnabled(false)
        answersAdapter.notifyDataSetChanged()
        squareAdapter.notifyDataSetChanged()
    }

    fun setSquareImage(squareList: MutableList<Int>) {

        val numberOfColumns = 10
        val gridLayoutManager =
            GridLayoutManager(activity, numberOfColumns, RecyclerView.VERTICAL, false)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int {
                if (position == 20) {
                    return 10
                }
                return 1
            }
        }

        recyclerSquareAll.layoutManager = gridLayoutManager
        recyclerSquareAll.setHasFixedSize(true)
        activity?.let { squareAdapter.addContext(it) }
        squareAdapter.setDataList(squareList)
        recyclerSquareAll.adapter = squareAdapter

    }

    private fun startAnimation() {

        txtCurrentQuizPosAll.animate().scaleX(1.3f).duration = 350
        txtCurrentQuizPosAll.animate().scaleY(1.3f).duration = 350
        txtQuizTextAll.animate().alpha(0.0f).duration = 1000
        recyclerAnswersAll.animate().alpha(0.0f).duration = 1000
    }

    private fun endAnimation() {

        txtCurrentQuizPosAll.animate().scaleX(1.0f).duration = 350
        txtCurrentQuizPosAll.animate().scaleY(1.0f).duration = 350
        txtQuizTextAll.animate().alpha(1.0f).duration = 1000
        recyclerAnswersAll.animate().alpha(1.0f).duration = 1000
    }


    override fun onPause() {
        super.onPause()
        txtCurrentQuizPosAll.clearAnimation()
        txtCurrentQuizPosAll.clearAnimation()
        txtQuizTextAll.clearAnimation()
        recyclerAnswersAll.clearAnimation()
    }


    fun onCompleteTest() {
        activity?.supportFragmentManager?.popBackStack()
    }


    fun getEndResultData(quizResult: QuizResult) {

        context?.alert(
            getString(R.string.attention_alert),
            getString(R.string.your_result).plus(" ${quizResult.user_points}/${quizResult.max_answers}"),
            {},
            false
        )
    }

}
