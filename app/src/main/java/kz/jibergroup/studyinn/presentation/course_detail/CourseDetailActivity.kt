package kz.jibergroup.studyinn.presentation.course_detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myfirstapp.presentation.global.utils.alert
import kotlinx.android.synthetic.main.activity_course_detail.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.GeneralResponse
import kz.jibergroup.studyinn.presentation.course_learning.CourseLearningActivity
import kz.jibergroup.studyinn.presentation.global.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

const val COURSE_ITEM = "courseItem"

class CourseDetailActivity : BaseActivity() {

    lateinit var adapter: CourseDetailPagerAdapter
    lateinit var courseItem: CourseItem

    val viewModel: CourseDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        intent.getParcelableExtra<CourseItem>(COURSE_ITEM)?.let {
            courseItem = it
            viewModel.courseItem.value = courseItem
        }

        viewModel.initCourseFragments()
        viewModel.courseItem.observe(this, courseObserver)
        viewModel.courseFragments.observe(this, viewPagerObserver)
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.result.observe(this, courseJoinObserver)


    }

    private val courseObserver = Observer<CourseItem> {
        initUi(it)
    }

    private val viewPagerObserver = Observer<MutableList<Fragment>> {
        initViewpager()
    }

    private val courseJoinObserver = Observer<GeneralResponse> {
        joinAction(it)
    }

    private fun joinAction(generalResponse: GeneralResponse) {
        if (generalResponse.success ?: false) {
            alert(
                getString(R.string.attention_alert),
                getString(R.string.you_have_joined_to_start_go_modules),
                {
                    updateCourseItem()
                },
                false
            )
        } else {
            alert(getString(R.string.attention_alert), generalResponse.message, {}, false)
        }
    }

    private fun updateCourseItem() {
        val course = viewModel.courseItem.value
        course?.is_my_course = true
        viewModel.courseItem.value = course
    }

    private fun initViewpager() {
        adapter = CourseDetailPagerAdapter(this.supportFragmentManager, this)
        adapter.submitFragments(viewModel.courseFragments.value ?: mutableListOf())
        adapter.submitData(courseItem)
        courseDetailViewPager.adapter = adapter
        courseDetailTab.setupWithViewPager(courseDetailViewPager)
    }


    private fun initUi(courseItem: CourseItem) {

        if (courseItem.is_my_course ?: false) {
            courseDetailJoinBtnCard.visibility = View.INVISIBLE
            courseDetailJoinBtnCard.isEnabled = false
            courseDetailAlreadyJoinedCard.visibility = View.VISIBLE

            courseDetailAlreadyJoinedBtn.setOnClickListener {
                //                onOpenLearningCourse()
                alert(
                    getString(R.string.attention_alert),
                    getString(R.string.to_continue_switch_modules),
                    {}
                )
            }

        } else {
            courseDetailAlreadyJoinedCard.visibility = View.GONE
            courseDetailJoinBtnCard.visibility = View.VISIBLE

            courseDetailJoinBtn.setOnClickListener {
                courseItem.id?.let {
                    viewModel.joinCourse(it)
                }
            }
        }
        courseDetailTitle.text = courseItem.title
        courseDetailFollowerCount.text = courseItem.user_counts.toString()
        courseItem.rating?.let {
            courseRating.text = courseItem.rating.toString()
        }
        courseRatingStars.rating =
            if (courseItem.rating != null) courseItem.rating.toFloat() else courseItem.id?.toFloat()
                ?: 0F
    }

    private fun onOpenLearningCourse() {
        val intent = Intent(this, CourseLearningActivity::class.java)
        startActivity(intent)
        finish()
    }

}

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

