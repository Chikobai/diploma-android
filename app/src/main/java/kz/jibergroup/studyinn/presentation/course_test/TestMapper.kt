package kz.jibergroup.studyinn.presentation.course_test

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Quiz(
    val id: Int? = null,
    val question: String? = null,
    val point: Int? = null,
    val answerList: MutableList<Answer>? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.createTypedArrayList(Answer.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(question)
        writeValue(point)
        writeTypedList(answerList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Quiz> = object : Parcelable.Creator<Quiz> {
            override fun createFromParcel(source: Parcel): Quiz = Quiz(source)
            override fun newArray(size: Int): Array<Quiz?> = arrayOfNulls(size)
        }
    }
}


data class Answer(
    val id: Int? = null,
    val title: String? = null,
    var is_correct: Boolean = false,
    var user_answer: Int = 0
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        1 == source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(title)
        writeInt((if (is_correct) 1 else 0))
        writeInt(user_answer)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Answer> = object : Parcelable.Creator<Answer> {
            override fun createFromParcel(source: Parcel): Answer = Answer(source)
            override fun newArray(size: Int): Array<Answer?> = arrayOfNulls(size)
        }
    }
}


fun getQuiz(): MutableList<Quiz> {
    val data = mutableListOf<Quiz>()

    data.add(
        Quiz(
            id = 0,
            question = "Abay was born?",
            point = 1,
            answerList = getQuestionsAnswer()
        )
    )
    data.add(
        Quiz(
            id = 1,
            question = "Abay was born?",
            point = 1,
            answerList = getQuestionsAnswer()
        )
    )
    data.add(
        Quiz(
            id = 2,
            question = "Abay was born?",
            point = 1,
            answerList = getQuestionsAnswer()
        )
    )

    return data
}

fun getQuestionsAnswer(): MutableList<Answer> {
    val data = mutableListOf<Answer>()

    data.add(Answer(id = 0, title = "Abay was born in 1845", is_correct = true))
    data.add(Answer(id = 1, title = "Abay was born in 1842", is_correct = false))
    data.add(Answer(id = 2, title = "Abay was born in 1847", is_correct = false))
    data.add(Answer(id = 3, title = "Abay was born in 1849", is_correct = false))
    data.add(Answer(id = 4, title = "Abay was born in 1855", is_correct = false))

    return data
}


data class QuizResult(

    val id: Int = 0,
    val user_id: Int = 0,
    var max_points: String? = null,
    var user_points: String? = null,
    var max_answers: String? = null,
    var user_answers: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(user_id)
        parcel.writeString(max_points)
        parcel.writeString(user_points)
        parcel.writeString(max_answers)
        parcel.writeString(user_answers)
        parcel.writeString(updated_at)
        parcel.writeString(created_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuizResult> {
        override fun createFromParcel(parcel: Parcel): QuizResult {
            return QuizResult(parcel)
        }

        override fun newArray(size: Int): Array<QuizResult?> {
            return arrayOfNulls(size)
        }
    }
}

