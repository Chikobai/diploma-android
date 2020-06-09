package android.ibanking.altyn.presentation.global.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder <E>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: E)
}