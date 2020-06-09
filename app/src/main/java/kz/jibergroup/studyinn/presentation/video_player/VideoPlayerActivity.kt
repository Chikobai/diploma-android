package kz.jibergroup.studyinn.presentation.video_player

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video_player.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.global.base.BaseActivity

const val VIDEO_URL_KEY = "VIDEO_URL_KEY"

class VideoPlayerActivity : BaseActivity(), Player.EventListener {

    //https://blog.mindorks.com/using-exoplayer-to-play-video-and-audio-in-android-like-a-pro

    lateinit var fullscreenButton: ImageView
    var fullscreen = false

    private var videoUrl = ""
    private var videoUrlDefault =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        overridePendingTransition(R.anim.swipe_left, R.anim.swipe_right)

        intent.getStringExtra(VIDEO_URL_KEY)?.let {
            videoUrl = it
        }

        initFullScreenMode()
        video_player_remove.setOnClickListener {
            finish()
        }
    }

    private fun initializeExoplayer() {

        //            mediaObjects.get(currentPlayingPosition).getMediaObject().setPlaybackPosition(videoPlayer.getCurrentPosition());
        //            videoPlayer.seekTo(mediaObjects.get(currentPlayingPosition).getMediaObject().getPlaybackPosition());

        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(adaptiveTrackSelectionFactory),
            DefaultLoadControl()
        )

        prepareExoplayer()
        video_view.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }
    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }


    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING)
            progressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY)
            progressBar.visibility = View.INVISIBLE
    }


    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val mediaDataSourceFactory = DefaultDataSourceFactory(
            this, Util.getUserAgent(
                this,
                "mediaPlayerSample"
            )
        )
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(uri)


        return mediaSource
    }

    private fun prepareExoplayer() {
        val uri = Uri.parse(videoUrlDefault)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
    }


    private fun initFullScreenMode() {
        fullscreenButton = video_view.findViewById(R.id.exo_fullscreen_icon)
        fullscreenButton.setOnClickListener {
            if (fullscreen) {
                fullscreenButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_fullscreen_open
                    )
                )
                getWindow()?.getDecorView()?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                val params = video_view.getLayoutParams() as RelativeLayout.LayoutParams
                params.width = RelativeLayout.LayoutParams.MATCH_PARENT
                params.height = 650
                video_view.setLayoutParams(params)
                fullscreen = false
                video_view.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
            } else {
                fullscreenButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_fullscreen_close
                    )
                )
                getWindow()?.getDecorView()?.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                val params = video_view.getLayoutParams() as RelativeLayout.LayoutParams
                params.width = RelativeLayout.LayoutParams.MATCH_PARENT
                params.height = RelativeLayout.LayoutParams.MATCH_PARENT
                video_view.setLayoutParams(params)
                fullscreen = true
            }
        }
    }


    override fun onStart() {
        super.onStart()

        initializeExoplayer()
    }

    override fun onStop() {

        releaseExoplayer()
        super.onStop()
    }


}



