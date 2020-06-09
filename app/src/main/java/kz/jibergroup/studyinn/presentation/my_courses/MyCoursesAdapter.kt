package kz.jibergroup.studyinn.presentation.my_courses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_my_course_shimmer.view.*
import kotlinx.android.synthetic.main.item_my_courses.view.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.ItemClickListener
import kz.jibergroup.studyinn.presentation.home.BaseViewHolder

class MyCoursesAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataSet: MutableList<CourseItem> = mutableListOf()
    lateinit var listener: ItemClickListener
    lateinit var context: Context

    fun submitData(data: MutableList<CourseItem>) {
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
                return R.layout.item_my_course_shimmer
            }
            DataInitType.REAL -> {
                return R.layout.item_my_courses
            }
            else -> {
                return R.layout.item_my_courses
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_my_courses -> MyCourseViewHolder(view)
            R.layout.item_my_course_shimmer -> ShimmerViewHolder(view)
            else -> MyCourseViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyCourseViewHolder -> holder.bind(dataSet[position])
            is ShimmerViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class MyCourseViewHolder(itemView: View) : BaseViewHolder<CourseItem>(itemView) {

        override fun bind(item: CourseItem) {
            itemView.itemMyCourseTitle.text = item.title
            itemView.itemMyCourseProgressTitle.text = item.module_counts.toString().plus(
                " " +context.getString(
                    R.string.lesson
                )
            )

            itemView.continueBtn.setOnClickListener {
                listener.click(-1, item)
            }

            itemView.myCourseCard.setOnClickListener {
                listener.click(adapterPosition, item)

            }
        }

    }

    inner class ShimmerViewHolder(itemView: View) : BaseViewHolder<CourseItem>(itemView) {
        override fun bind(item: CourseItem) {
            itemView.itemMyCourseShimmer.scrollBarFadeDuration = 1000
            itemView.itemMyCourseShimmer.startShimmer()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}
