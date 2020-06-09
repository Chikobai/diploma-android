package kz.jibergroup.studyinn.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_certificate.view.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.home.BaseViewHolder
import kz.jibergroup.studyinn.presentation.home.ItemClickListener
import kz.jibergroup.studyinn.presentation.image_dialog.ImageItem

class CertificateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataSet: MutableList<ImageItem> = mutableListOf()
    lateinit var listener: ItemClickListener

    fun submitData(data: MutableList<ImageItem>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun submitListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_certificate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_certificate -> CertificateViewHolder(view)
            else -> CertificateViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CertificateViewHolder -> holder.bind(dataSet[position])
        }
    }

    inner class CertificateViewHolder(itemView: View) : BaseViewHolder<ImageItem>(itemView) {

        override fun bind(item: ImageItem) {


            Glide.with(itemView.itemCertificateImage.context).load(dataSet[position].link)
                .into(itemView.itemCertificateImage)

            itemView.setOnClickListener {
                listener.click(adapterPosition)
            }


        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}
