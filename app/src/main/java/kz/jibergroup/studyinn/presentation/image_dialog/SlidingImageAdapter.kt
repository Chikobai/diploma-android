package kz.jibergroup.studyinn.presentation.image_dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*
import kz.jibergroup.studyinn.R

class SlidingImageAdapter(private val context: Context) : PagerAdapter() {

    lateinit var dataSet: MutableList<ImageItem>
    var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    fun submitData(data: MutableList<ImageItem>) {
        this.dataSet = data
        notifyDataSetChanged()
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.item_image, view, false)

        Glide.with(context).load(dataSet[position].link).into(imageLayout.itemImage)

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


}