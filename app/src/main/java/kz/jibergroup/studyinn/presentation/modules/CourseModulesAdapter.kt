package kz.jibergroup.studyinn.presentation.modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_module_child.view.*
import kotlinx.android.synthetic.main.item_module_parent.view.*
import kz.jibergroup.studyinn.R

class CourseModulesAdapter(
    groups: MutableList<ModuleItem>,
    private val onItemClickListener: ((moduleId: Int?, lessonId: Int?) -> Unit)? = null
) : ExpandableRecyclerViewAdapter<CourseModulesAdapter.ModuleParentViewHolder,
        CourseModulesAdapter.ModuleChildViewHolder>(groups) {

    override fun onCreateGroupViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): ModuleParentViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_module_parent, parent, false)
        return ModuleParentViewHolder(view)
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): ModuleChildViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_module_child, parent, false)
        return ModuleChildViewHolder(view)
    }

    override fun onBindGroupViewHolder(
        holder: ModuleParentViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        val data = group as ModuleItem
        holder?.title?.text = "${flatPosition + 1}.".plus(data.title)

        if (data.modules?.isEmpty() ?: true) {
            holder?.arrow?.visibility = View.GONE
        } else {
            holder?.arrow?.visibility = View.VISIBLE
        }


    }

    override fun onBindChildViewHolder(
        holder: ModuleChildViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val data = group as ModuleItem
        val item = data.items?.get(childIndex)
        holder?.title?.text = item?.title

        holder?.itemView?.setOnClickListener { onItemClickListener?.invoke(data.id, item?.id) }


    }

    inner class ModuleParentViewHolder(itemView: View) : GroupViewHolder(itemView) {
        val title: TextView = itemView.itemModuleParentTitle
        val points: TextView = itemView.itemModuleParentSubTitle
        val arrow: ImageView = itemView.itemModuleParentArrow

        override fun expand() {
            arrow.setColorFilter(
                ContextCompat.getColor(
                    arrow.context,
                    R.color.materialGray
                ), android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }

        override fun collapse() {
            arrow.setColorFilter(
                ContextCompat.getColor(
                    arrow.context,
                    R.color.colorBlack
                ), android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }

    inner class ModuleChildViewHolder(itemView: View) : ChildViewHolder(itemView) {
        val title = itemView.itemModuleChildTitle
        val points = itemView.itemModuleChildPoint
        val time = itemView.itemModuleChildDuration
        val pupilCount = itemView.itemModuleChildPersonCount
        val likeCount = itemView.itemModuleChildLike
    }

}
