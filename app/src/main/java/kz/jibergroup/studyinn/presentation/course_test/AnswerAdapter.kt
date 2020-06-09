package kz.jibergroup.studyinn.presentation.course_test

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kz.jibergroup.studyinn.R

class AnswersAdapter : RecyclerView.Adapter<AnswersAdapter.QuizViewHolder>() {

    var itemOnClick: CompoundButton.OnCheckedChangeListener? = null
    private var answerList: MutableList<Answer> = mutableListOf()
    private lateinit var context: Context
    private var isEnabled:Boolean = true


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_answer, parent, false)
        return itemOnClick?.let { QuizViewHolder(view, it) }!!
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(viewHolder: QuizViewHolder, position: Int) {


        viewHolder.apply {

            checkBox.apply {

                tag = answerList[position]
                text = answerList[position].title

                Log.d("LOG_IS_RIGHT", answerList[position].user_answer.toString())

                isEnabled = this@AnswersAdapter.isEnabled

                if (!isEnabled) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        buttonTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
                    }

                    setTextColor(ContextCompat.getColor(context, R.color.colorDark))
                }

                when (answerList[position].user_answer) {
                    -1 -> {

                        setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            buttonTintList = ContextCompat.getColorStateList(context, R.color.colorWhite)
                        }

                        relAnswers.background = ContextCompat.getDrawable(context, R.drawable.rounded_answer_false)

                    }
                    1 -> {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            buttonTintList = ContextCompat.getColorStateList(context, R.color.colorWhite)
                        }

                        setTextColor(ContextCompat.getColor(context, R.color.colorWhite))

                        relAnswers.background = ContextCompat.getDrawable(context, R.drawable.rounded_answer_true)

                    }
                    else -> {

                        setTextColor(ContextCompat.getColor(context, R.color.colorDark))

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            buttonTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
                        }


                        relAnswers.background = ContextCompat.getDrawable(context, R.drawable.rounded_answer_default)
                        isChecked = false
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return answerList.size
    }

    fun setDataList(answers: MutableList<Answer>?) {
        answers?.let {
            answerList = it
            notifyDataSetChanged()
        }
    }

    fun addContext(context: Context) {
        this.context = context
    }

    fun setOnClickCheckBox(onClick: CompoundButton.OnCheckedChangeListener?){
        this.itemOnClick = onClick

    }

    fun setIsEnabled(boolean: Boolean){
        this.isEnabled = boolean
    }

    class QuizViewHolder(view: View, onClickListener: CompoundButton.OnCheckedChangeListener) :
        RecyclerView.ViewHolder(view) {


        val relAnswers: RelativeLayout = itemView.findViewById(R.id.relItemAnswer)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxAnswerItem)


        init {
            checkBox.setOnCheckedChangeListener(onClickListener)
        }

    }

}

