package kz.jibergroup.studyinn.presentation.course_test

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kz.jibergroup.studyinn.R




class SquareGridAdapter : RecyclerView.Adapter<SquareGridAdapter.ViewHolder>() {


    private lateinit var context: Context
    private  var currentPos: Int = 0
    private var mData: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_square, parent, false)
        return  ViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {

            when (mData[position]) {
                -1 -> {
                    imgSquareItem.setImageResource(R.drawable.ic_17)
                }
                0 -> {
                    imgSquareItem.setImageResource(R.drawable.ic_9)
                }
                1 -> {
                    imgSquareItem.setImageResource(R.drawable.ic_16)
                }
            }


            if (position == currentPos) {
                imgSquareItem.setImageResource(R.drawable.ic_8)
            }

            if (position > 7) {
                //setMargins(imgSquareItem, 40, 0, 0, 0)
                //relQuizItem.setPadding(20, 0, 0, 0)
            } else {
                //relQuizItem.setPadding(0, 0, 0, 0)
                //setMargins(imgSquareItem, 0, 0, 0, 0)
            }

        }


    }

    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    fun setDataList(data: MutableList<Int>) {
        mData = data
        notifyDataSetChanged()
    }

    fun setCurrentPos(current_pos:Int){
        Log.d("LOG_CURRENT_POS", current_pos.toString())
        this.currentPos = current_pos
        notifyDataSetChanged()
    }

    fun addContext(context: Context) {
        this.context = context
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val imgSquareItem: ImageView = itemView.findViewById(R.id.imgSquareItem)
        val relQuizItem: RelativeLayout = itemView.findViewById(R.id.relQuizItem)

    }

}