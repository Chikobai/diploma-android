package kz.jibergroup.studyinn.presentation.courseinfo

import android.content.Context
import android.content.Intent
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.course_info_fragment.*
import kotlinx.android.synthetic.main.course_info_fragment.preview_image
import kotlinx.android.synthetic.main.course_info_fragment.preview_play
import kotlinx.android.synthetic.main.course_video_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.CourseSkill
import kz.jibergroup.studyinn.domain.entity.Owner
import kz.jibergroup.studyinn.presentation.video_player.VIDEO_URL_KEY
import kz.jibergroup.studyinn.presentation.video_player.VideoPlayerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseInfoFragment : BaseFragment() {

    companion object {
        fun newInstance(courseItem: CourseItem): CourseInfoFragment {
            val fragment = CourseInfoFragment()
            val args = Bundle()
            args.putParcelable("REPLACE WITH A STRING CONSTANT", courseItem)
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId(): Int {
        return R.layout.course_info_fragment
    }

    lateinit var courseItem: CourseItem
    val viewModel: CourseInfoViewModel by viewModel()

    private var videoUrlDefault =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.courseItem.observe(viewLifecycleOwner, courseObserver)
        viewModel.courseSkills.observe(viewLifecycleOwner, skillObserver)
        viewModel.courseTeachers.observe(viewLifecycleOwner, teachersObserver)

        Glide.with(activity as AppCompatActivity)
            .asBitmap()
            .load(videoUrlDefault)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(preview_image)


        preview_play.setOnClickListener {
            openPlayerActivity()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<CourseItem>("REPLACE WITH A STRING CONSTANT")?.let {
            courseItem = it
            viewModel.initCourse(courseItem)
        }
    }

    private val courseObserver = Observer<CourseItem> {
        initUi()
    }

    private val skillObserver = Observer<MutableList<CourseSkill>> {
        initSkills()
    }

    private val teachersObserver = Observer<MutableList<Owner>> {
        initTeachers()
    }

    private fun initUi() {
        lessonTitle.text = courseItem.title
        courseDescription.text = courseItem.info
        courseLanguage.text = courseItem.language
    }

    private fun initSkills() {
        val adapter = SkillAdapter()
        adapter.submitData(viewModel.courseSkills.value ?: mutableListOf())

        val layoutManager = FlexboxLayoutManager(activity)
        layoutManager.setFlexWrap(FlexWrap.WRAP)
        layoutManager.setFlexDirection(FlexDirection.ROW)
        layoutManager.setAlignItems(AlignItems.STRETCH)
        skillRecycler.layoutManager = layoutManager
        skillRecycler.adapter = adapter

    }

    private fun initTeachers() {
        val adapter = TeacherAdapter()
        adapter.submitData(viewModel.courseTeachers.value ?: mutableListOf())

        teacherRecycler.layoutManager = LinearLayoutManager(activity)
        teacherRecycler.adapter = adapter

    }


    private fun openPlayerActivity(){
        val intent = Intent(activity, VideoPlayerActivity::class.java)
        intent.putExtra(VIDEO_URL_KEY,viewModel.courseItem.value?.video_url)
        startActivity(intent)

    }

}
