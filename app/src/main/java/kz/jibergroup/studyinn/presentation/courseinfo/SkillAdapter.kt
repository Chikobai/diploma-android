package kz.jibergroup.studyinn.presentation.courseinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_skill.view.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseSkill
import kz.jibergroup.studyinn.presentation.home.BaseViewHolder
import kz.jibergroup.studyinn.presentation.home.ItemClickListener

class SkillAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var dataSet: MutableList<CourseSkill>
    lateinit var listener: ItemClickListener

    fun submitData(data: MutableList<CourseSkill>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun submitListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_skill
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_skill -> SkillViewHolder(view)
            else -> SkillViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SkillViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class SkillViewHolder(itemView: View) : BaseViewHolder<CourseSkill>(itemView) {

        override fun bind(item: CourseSkill) {

            itemView.itemSkillTitle.text = item.name

        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}
