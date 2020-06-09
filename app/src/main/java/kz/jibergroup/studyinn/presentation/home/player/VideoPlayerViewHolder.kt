package kz.jibergroup.studyinn.presentation.home.player

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_post.view.*

import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.PostItem
import kz.jibergroup.studyinn.utils.returnMonthTitleFromLongMillis


class VideoPlayerViewHolder(internal var parent: View) : RecyclerView.ViewHolder(parent) {

    lateinit var media_container: FrameLayout
    lateinit var title: TextView
    lateinit var thumbnail: ImageView
    lateinit var volumeControl: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var requestManager: RequestManager
    lateinit var postAuthor: TextView
    lateinit var postTitle: TextView
    lateinit var postPublishDate: TextView
    lateinit var postDesc: TextView

    init {
        media_container = parent.findViewById(R.id.media_container)
        thumbnail = parent.findViewById(R.id.thumbnail)
        progressBar = parent.findViewById(R.id.progressBar)
        volumeControl = parent.findViewById(R.id.volume_control)

        postAuthor = parent.findViewById(R.id.postAuthor)
        postTitle = parent.findViewById(R.id.postTitle)
        postPublishDate = parent.findViewById(R.id.postPublishDate)
        postDesc = parent.findViewById(R.id.postDesc)

    }

    fun onBind(item: PostItem, requestManager: RequestManager) {
        this.requestManager = requestManager
        parent.tag = this
        this.requestManager
            .load(item.mediaObject?.thumbnail)
            .into(thumbnail)

        postAuthor.text =
            item.teacher?.first_name.plus(" ").plus(item.teacher?.last_name)
                .plus(" $adapterPosition")
        postTitle.text = item.title
        postPublishDate.text = returnMonthTitleFromLongMillis(
            item.dateCreate?.toLong()
                ?: -1L
        )
        postDesc.text = item.description

        if (item.description?.length?.compareTo(65) == 1) {
            itemView.postDesc.text =
                item.description.subSequence(0, 65).toString().plus("More")

            itemView.postDesc.setOnClickListener {
                itemView.postDesc.text = item.description
            }
        }
    }

}














