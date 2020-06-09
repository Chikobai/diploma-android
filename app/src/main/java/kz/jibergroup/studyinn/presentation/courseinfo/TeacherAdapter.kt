package kz.jibergroup.studyinn.presentation.courseinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_teacher.view.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.Owner
import kz.jibergroup.studyinn.presentation.home.BaseViewHolder
import kz.jibergroup.studyinn.presentation.home.ItemClickListener

class TeacherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataSet: MutableList<Owner> = mutableListOf()
    lateinit var listener: ItemClickListener

    fun submitData(data: MutableList<Owner>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun submitListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_teacher
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_teacher -> TeacherViewHolder(view)
            else -> TeacherViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TeacherViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class TeacherViewHolder(itemView: View) : BaseViewHolder<Owner>(itemView) {

        override fun bind(item: Owner) {
            itemView.itemTeacherName.text = item.first_name.plus(" " + item.last_name)
            itemView.itemTeacherExperience.text = item.email
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}