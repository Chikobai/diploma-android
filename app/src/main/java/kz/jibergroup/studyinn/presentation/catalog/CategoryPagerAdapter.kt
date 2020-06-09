package kz.jibergroup.studyinn.presentation.catalog

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class CategoryPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = items.size
    var items = mutableListOf<Fragment>()
    var title = mutableListOf<String>()

    fun submitFragments(items: MutableList<Fragment>) {
        this.items = items
    }

    fun submitCategoryTitle(labels: MutableList<String>) {
        this.title = labels
    }

    override fun getItem(i: Int): Fragment {
        return items[i]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }
}