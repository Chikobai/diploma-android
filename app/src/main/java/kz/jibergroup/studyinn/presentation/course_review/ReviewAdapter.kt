package kz.jibergroup.studyinn.presentation.course_review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post_shimmer.view.*
import kotlinx.android.synthetic.main.item_review.view.*
import kotlinx.android.synthetic.main.item_reviewer_shimmer.view.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.PostItem
import kz.jibergroup.studyinn.domain.entity.ReviewItem
import kz.jibergroup.studyinn.presentation.home.BaseViewHolder

class ReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataSet: MutableList<ReviewItem> = mutableListOf()

    fun submitData(data: MutableList<ReviewItem>) {
        this.dataSet = data
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        when (dataSet.get(position).dateInitType) {
            DataInitType.SHIMMER -> {
                return R.layout.item_reviewer_shimmer
            }
            DataInitType.REAL -> {
                return R.layout.item_review
            }
            else -> {
                return R.layout.item_review
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_review -> ReviewViewHolder(view)
            R.layout.item_reviewer_shimmer -> ShimmerViewHolder(view)
            else -> ReviewViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReviewViewHolder -> holder.bind(dataSet[position])
            is ShimmerViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class ReviewViewHolder(itemView: View) : BaseViewHolder<ReviewItem>(itemView) {

        override fun bind(item: ReviewItem) {
            itemView.itemReviewName.text = item.reviewer?.first_name.plus(item.reviewer?.last_name)
            itemView.itemReviewText.text = item.text
            itemView.itemReviewRating.rating = item.rating?.toFloat() ?: -1F

        }

    }

    inner class ShimmerViewHolder(itemView: View) : BaseViewHolder<ReviewItem>(itemView) {
        override fun bind(item: ReviewItem) {
            itemView.shimmerReviewItem.scrollBarFadeDuration = 1000
            itemView.shimmerReviewItem.startShimmer()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}
