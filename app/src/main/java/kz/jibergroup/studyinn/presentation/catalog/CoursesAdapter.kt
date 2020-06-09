package kz.jibergroup.studyinn.presentation.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_course.view.*
import kotlinx.android.synthetic.main.item_course_shimmer.view.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem
import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.ItemClickListener
import kz.jibergroup.studyinn.presentation.home.BaseViewHolder

class CoursesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                return R.layout.item_course_shimmer
            }
            DataInitType.REAL -> {
                return R.layout.item_course
            }
            else -> {
                return R.layout.item_course
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_course -> CourseViewHolder(view)
            R.layout.item_course_shimmer -> ShimmerViewHolder(view)
            else -> CourseViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourseViewHolder -> holder.bind(dataSet[position])
            is ShimmerViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class CourseViewHolder(itemView: View) : BaseViewHolder<CourseItem>(itemView) {

        override fun bind(item: CourseItem) {

            itemView.itemCourseTitle.text = item.title
            itemView.itemCourseFollowerCount.text = item.user_counts.toString().plus(
                " " +context.getString(
                    R.string.members
                )
            )
            itemView.itemCourseLessonCount.text =
                item.module_counts.toString().plus(" " + context.getString(R.string.lesson))

            itemView.setOnClickListener {
                listener.click(adapterPosition, item)
            }

        }

    }

    inner class ShimmerViewHolder(itemView: View) : BaseViewHolder<CourseItem>(itemView) {
        override fun bind(item: CourseItem) {
            itemView.itemCourseShimmer.scrollBarFadeDuration = 1000
            itemView.itemCourseShimmer.startShimmer()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}
