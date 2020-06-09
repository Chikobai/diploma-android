package kz.jibergroup.studyinn.presentation.course_video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.course_video_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import kz.jibergroup.studyinn.presentation.video_player.VIDEO_URL_KEY
import kz.jibergroup.studyinn.presentation.video_player.VideoPlayerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.DiskCacheStrategy


class CourseVideoFragment : BaseFragment(), Player.EventListener {

    companion object {
        private const val courseItemKey = "Course_Item"

        fun newInstance(label: String?): CourseVideoFragment {

            val fragment = CourseVideoFragment()
            val args = Bundle()
            args.putString(courseItemKey, label)
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId(): Int {
        return R.layout.course_video_fragment
    }

    val viewModel: CourseVideoViewModel by viewModel()
    private var videoUrl = ""
    private var videoUrlDefault =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(courseItemKey)?.let {
            videoUrl = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Glide.with(activity as AppCompatActivity)
            .asBitmap()
            .load(videoUrlDefault)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(preview_image)


        preview_play.setOnClickListener {
            openPlayerActivity()
        }
    }

    private fun openPlayerActivity(){
        val intent = Intent(activity,VideoPlayerActivity::class.java)
        intent.putExtra(VIDEO_URL_KEY,videoUrl)
        startActivity(intent)

    }


}
