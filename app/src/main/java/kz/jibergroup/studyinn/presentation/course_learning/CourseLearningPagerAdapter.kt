package kz.jibergroup.studyinn.presentation.course_learning

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CourseLearningPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = items.size
    var items: List<CourseLearningViewModel.FragmentItem> = listOf()

    fun submitFragments(items: List<CourseLearningViewModel.FragmentItem>) {
        this.items = items
    }

    override fun getItem(i: Int): Fragment {
        return items.get(i).fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return ""
    }
}