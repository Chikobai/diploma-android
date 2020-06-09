package kz.jibergroup.studyinn.presentation.home.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.PostItem


class VideoPlayerRecyclerAdapter(
    private val mediaObjects: ArrayList<PostItem>,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return VideoPlayerViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_post,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        (viewHolder as VideoPlayerViewHolder).onBind(mediaObjects[i], requestManager)
    }

    override fun getItemCount(): Int {
        return mediaObjects.size
    }

}














