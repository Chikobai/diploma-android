package kz.jibergroup.studyinn.presentation.course_detail

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.CourseItem

class CourseDetailPagerAdapter(fm: FragmentManager,private val context: Context) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = items.size
    var items: List<Fragment> = listOf()
    var title: List<String> = listOf(context.getString(R.string.info),context.getString(R.string.reviews),context.getString(R.string.modules))
    var dataSet: CourseItem? = null

    fun submitData(data: CourseItem) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun submitFragments(items: List<Fragment>) {
        this.items = items
    }

    override fun getItem(i: Int): Fragment {
        return items[i]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }
}