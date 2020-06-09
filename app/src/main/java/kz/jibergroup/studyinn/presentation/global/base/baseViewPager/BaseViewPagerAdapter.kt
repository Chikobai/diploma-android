package android.ibanking.altyn.presentation.global.base.baseViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BaseViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    var dataSet: MutableList<ViewPagerItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        return dataSet[position].fragment
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        super.getPageTitle(position)
        return dataSet[position].title
    }
}