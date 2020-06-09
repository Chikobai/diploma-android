package kz.jibergroup.studyinn.presentation.course_learning

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.example.myfirstapp.presentation.global.utils.EventObserver
import kotlinx.android.synthetic.main.activity_course_learning.*
import kotlinx.android.synthetic.main.error_message_view.*
import kotlinx.android.synthetic.main.error_not_found.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.global.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

const val MODULE_ID_KEY = "MODULE_ID_KEY"
const val LESSON_ID_KEY = "LESSON_ID_KEY"

class CourseLearningActivity : BaseActivity() {

    val viewModel: CourseLearningViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_learning)

        viewModel.moduleId.value = intent.getIntExtra(MODULE_ID_KEY, -1)
        viewModel.lessonId.value = intent.getIntExtra(LESSON_ID_KEY, -1)

        setSupportActionBar(courseLearningToolbar)

        viewModel.state.observe(this, viewStateObserver)
        viewModel.fragmentItems.observe(this, pagesObserver)
        viewModel.errorLiveData.observe(this, errObserver)

//        viewModel.getCourseLessonPages(
//            viewModel.moduleId.value ?: -1,
//            viewModel.lessonId.value ?: -1
//        )

        viewModel.getCourseLessonPages(
            1,
            1
        )

    }

    private val pagesObserver = Observer<MutableList<CourseLearningViewModel.FragmentItem>> {
        initCoursePager()
    }

    private val viewStateObserver = Observer<CourseLearningViewModel.ViewMessage> { state ->
        handleView(state)
    }

    protected val errObserver = EventObserver<String> {
        viewModel.state.value =
            CourseLearningViewModel.ViewMessage(CourseLearningViewModel.ViewState.Error)
    }


    private fun initCoursePager() {

        courseLearningTabLayout.setupWithViewPager(courseLearningViewPager)
        supportActionBar?.title = viewModel.lesson.value?.title

        val adapter = CourseLearningPagerAdapter(supportFragmentManager)
        adapter.submitFragments(viewModel.fragmentItems.value ?: mutableListOf())
        courseLearningViewPager.adapter = adapter

        viewModel.fragmentItems.value?.forEachIndexed { index, pageItem ->
            var iconId = -1
            when (pageItem.lessonType) {
                CourseLearningViewModel.LessonType.Content -> {
                    iconId = R.drawable.ic_book
                }
                CourseLearningViewModel.LessonType.Video -> {
                    iconId = R.drawable.ic_play
                }
                CourseLearningViewModel.LessonType.Survey -> {
                    iconId = R.drawable.ic_survey
                }
            }
            courseLearningTabLayout.getTabAt(index)?.setIcon(iconId)
        }
    }

    private fun handleView(viewMessage: CourseLearningViewModel.ViewMessage) {
        when (viewMessage.errorMessageView) {
            CourseLearningViewModel.ViewState.Data -> {
                lessonProgressBar.visibility = View.GONE
                error404Layout.visibility = View.GONE
                errorLayout.visibility = View.GONE
                lessonContainer.visibility = View.VISIBLE
                viewModel.initLessonPages()
            }
            CourseLearningViewModel.ViewState.Loading -> {
                error404Layout.visibility = View.GONE
                errorLayout.visibility = View.GONE
                lessonContainer.visibility = View.GONE
                lessonProgressBar.visibility = View.VISIBLE
            }
            CourseLearningViewModel.ViewState.Error -> {
                lessonProgressBar.visibility = View.GONE
                error404Layout.visibility = View.GONE
                lessonContainer.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE

                errorTryAgain.setOnClickListener {
                    viewModel.state.value =
                        CourseLearningViewModel.ViewMessage(CourseLearningViewModel.ViewState.Loading)
                    viewModel.getCourseLessonPages(3, 3)
                }

            }
            CourseLearningViewModel.ViewState.Notfound -> {
                lessonProgressBar.visibility = View.GONE
                errorLayout.visibility = View.GONE
                lessonContainer.visibility = View.GONE
                error404Layout.visibility = View.VISIBLE

                error404TryAgain.setOnClickListener {
                    viewModel.state.value =
                        CourseLearningViewModel.ViewMessage(CourseLearningViewModel.ViewState.Loading)
                    viewModel.getCourseLessonPages(3, 3)
                }
            }
            else -> {

            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_course, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_attention -> {
                true
            }
            R.id.action_share -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
