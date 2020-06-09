package kz.jibergroup.studyinn.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.android.synthetic.main.item_post_shimmer.view.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.PostItem
import kz.jibergroup.studyinn.utils.returnMonthTitleFromLongMillis

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataSet: MutableList<PostItem> = mutableListOf()
    lateinit var listener: ItemClickListener
    lateinit var context: Context

    fun submitData(data: MutableList<PostItem>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun submitContext(context: Context) {
        this.context = context
    }


    fun submitListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        when (dataSet.get(position).dataInitType) {
            DataInitType.SHIMMER -> {
                return R.layout.item_post_shimmer
            }
            DataInitType.REAL -> {
                return R.layout.item_post
            }
            else -> {
                return R.layout.item_post
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_post -> PostViewHolder(view)
            R.layout.item_post_shimmer -> ShimmerViewHolder(view)
            else -> PostViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(dataSet[position])
            is ShimmerViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class PostViewHolder(itemView: View) : BaseViewHolder<PostItem>(itemView) {

        override fun bind(item: PostItem) {
//
//            Glide.with(activity as AppCompatActivity).load(item.teacher)
//                .into(itemView.postTeacherImage)
            itemView.postAuthor.text =
                item.teacher?.first_name.plus(" ").plus(item.teacher?.last_name)
            itemView.postTitle.text = item.title
            itemView.postPublishDate.text = returnMonthTitleFromLongMillis(
                item.dateCreate?.toLong()
                    ?: -1L
            )
            itemView.postDesc.text = item.description

            if (item.description?.length?.compareTo(65) == 1) {
                itemView.postDesc.text =
                    item.description.subSequence(0, 65).toString().plus(context.getString(R.string.more))

                itemView.postDesc.setOnClickListener {
                    itemView.postDesc.text = item.description
                }
            }


        }

    }

    inner class ShimmerViewHolder(itemView: View) : BaseViewHolder<PostItem>(itemView) {
        override fun bind(item: PostItem) {
            itemView.itemPostShimmer.scrollBarFadeDuration = 1000
            itemView.itemPostShimmer.startShimmer()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}

abstract class BaseViewHolder<E>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: E)
}