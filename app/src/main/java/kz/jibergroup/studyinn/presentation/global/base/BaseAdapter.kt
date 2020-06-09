package android.ibanking.altyn.presentation.global.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_empty.view.*
import kz.jibergroup.studyinn.R

abstract class BaseAdapterIsEmpty<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * [isNotify] флаг для уведомления что данные пришли в [dataSet]
     * [dataSet] Получение данных для [BaseAdapterIsEmpty] адаптера
     * [backButtonEvent] Событие при нажатии назад
     * [returnSize] получение размерности списка (не много хакнутый)
     * [delegateViewHolder] в него мы делегируем любой ViewHolder в дочернем классе
     * [textNotification] уведомление которое хотите вывести
     */

    private var isNotify = false
    private var textNotification: String? = null
    private var showButton: Boolean = true

    var dataSet: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            isNotify = true
            notifyDataSetChanged()
        }

    var backButtonEvent: (() -> Unit)? = null


    override fun getItemCount(): Int = returnSize()

    private fun returnSize(): Int = when (isNotify) {
        true -> if (dataSet.isEmpty()) {
            1
        } else {
            isNotify = false
            dataSet.size
        }
        false -> dataSet.size
    }


    internal fun setTextNotificationEmptyItem(value: String) {
        textNotification = value
    }

    internal fun showButton(show: Boolean) {
        showButton = show
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (dataSet.isEmpty()) {
            EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false))
        } else {
            delegateViewHolder(parent, viewType)
        }
    }


    abstract fun delegateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseAdapterIsEmpty<*>.EmptyViewHolder) {
            holder.bind(EmptyAdapter(
                    "sdcsdc", textNotification ?: ""
            ))
        }
    }


    private inner class EmptyViewHolder(itemView: View) : BaseViewHolder<EmptyAdapter>(itemView) {
        override fun bind(item: EmptyAdapter) {
//            with(item) {
//                itemView.textNotification.text = notification
//                if (!showButton) itemView.backButton.visibility = View.GONE
//                itemView.backButton.setOnClickListener {
//                    backButtonEvent?.invoke()
//                }
//            }
        }
    }


}


data class EmptyAdapter(val icon: String, val notification: String)