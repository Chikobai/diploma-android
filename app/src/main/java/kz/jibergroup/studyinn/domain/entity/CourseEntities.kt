package kz.jibergroup.studyinn.domain.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kz.jibergroup.studyinn.presentation.home.player.MediaObject
import java.util.*

@Keep
data class GeneralResponse(
    val success: Boolean? = false,
    val message: String? = null
)

@Keep
data class ReviewItem(

    val id: Int? = null,
    val text: String? = null,
    val rating: String? = null,
    val created: Date? = null,
    val created_in_sec: Long? = null,
    val reviewer: Reviewer? = null,
    var dateInitType: DataInitType = DataInitType.REAL
)

@Keep
data class Reviewer(

    val id: Int? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null
)

@Keep
data class ReviewResponse(

    val meta: MetaItem? = null,
    val count: Int? = null,
    val data: MutableList<ReviewItem>? = null
)


@Keep
data class ModuleItem(
    val id: Int?,
    val titleParent: String?,
    val result: Result?,
    val lessons: MutableList<Lesson>?
) : ExpandableGroup<Lesson>(titleParent, lessons)

@Keep
data class Result(

    val count: Int? = null,
    val passed: Int? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(count)
        writeValue(passed)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Result> = object : Parcelable.Creator<Result> {
            override fun createFromParcel(source: Parcel): Result = Result(source)
            override fun newArray(size: Int): Array<Result?> = arrayOfNulls(size)
        }
    }
}

@Keep
data class Lesson(

    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val time: String? = null,
    val result: Result? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Result>(Result::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(title)
        writeString(description)
        writeString(time)
        writeParcelable(result, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Lesson> = object : Parcelable.Creator<Lesson> {
            override fun createFromParcel(source: Parcel): Lesson = Lesson(source)
            override fun newArray(size: Int): Array<Lesson?> = arrayOfNulls(size)
        }
    }
}

@Keep
data class ModuleResponse(

    val meta: MetaItem? = null,
    val count: Int? = null,
    val data: MutableList<ModuleItem>? = null
)

@Keep
data class CourseItem(

    val id: Int? = null,
    val title: String? = null,
    val name: String? = null,
    val info: String? = null,
    val video_url: String? = null,
    val language: String? = null,
    val rating: Double? = null,
    val user_counts: Int? = null,
    var is_my_course: Boolean? = null,
    val module_counts: Int? = null,
    val owner: Owner? = null,
    val course_skills: List<CourseSkill>? = null,
    val firstName: String? = null,
    val email: String? = null,
    val dataInitType: DataInitType = DataInitType.REAL
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readParcelable<Owner>(Owner::class.java.classLoader),
        source.createTypedArrayList(CourseSkill.CREATOR),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(title)
        writeString(name)
        writeString(info)
        writeString(video_url)
        writeString(language)
        writeValue(rating)
        writeValue(user_counts)
        writeValue(is_my_course)
        writeValue(module_counts)
        writeParcelable(owner, 0)
        writeTypedList(course_skills)
        writeString(firstName)
        writeString(email)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CourseItem> = object : Parcelable.Creator<CourseItem> {
            override fun createFromParcel(source: Parcel): CourseItem = CourseItem(source)
            override fun newArray(size: Int): Array<CourseItem?> = arrayOfNulls(size)
        }
    }
}


@Keep
data class Owner(

    val id: Int? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(email)
        writeString(first_name)
        writeString(last_name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Owner> = object : Parcelable.Creator<Owner> {
            override fun createFromParcel(source: Parcel): Owner = Owner(source)
            override fun newArray(size: Int): Array<Owner?> = arrayOfNulls(size)
        }
    }
}

@Keep
data class CourseSkill(

    val code: Int? = null,
    val name: String? = null

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(code)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CourseSkill> =
            object : Parcelable.Creator<CourseSkill> {
                override fun createFromParcel(source: Parcel): CourseSkill = CourseSkill(source)
                override fun newArray(size: Int): Array<CourseSkill?> = arrayOfNulls(size)
            }
    }
}

@Keep
data class CourseResponse(

    val meta: MetaItem? = null,
    val count: Int? = null,
    val data: MutableList<CourseItem>? = null
)

@Keep
data class MyCoursesItem(
    val course: CourseItem? = null
)

@Keep
data class MyCoursesResponse(

    val meta: MetaItem? = null,
    val count: Int? = null,
    val data: MutableList<CourseItem>? = null
)

interface ItemClickListener {
    fun click(position: Int, courseItem: CourseItem)
}


@Keep
data class Teacher(

    val last_name: String? = null,
    val id: Int? = null,
    val first_name: String? = null,
    val email: String? = null
)

@Keep
data class Course(

    val videoUrl: String? = null,
    val name: String? = null,
    val id: Int? = null,
    val title: String? = null,
    val info: String? = null
)

@Keep
data class PostItem(

    val urlType: Int? = null,
    val dateUpdate: String? = null,
    val description: String? = null,
    val title: String? = null,
    val url: String? = null,
    val url_type: Int? = null,
    val teacher: Teacher? = null,
    val dateCreate: String? = null,
    val viewed: Int? = null,
    val course: Course? = null,
    val postType: Int? = null,
    val id: Int? = null,
    val dateUpdatedInSec: Int? = null,
    val dateCreateInSec: Int? = null,
    var dataInitType: DataInitType = DataInitType.REAL,
    var mediaObject: MediaObject? = null

)

enum class DataInitType {
    REAL, SHIMMER
}


@Keep
data class PostResponse(

    val meta: MetaItem? = null,
    val count: Int? = null,
    val data: MutableList<PostItem>? = null
)

@Keep
data class MetaItem(

    val next: String? = null,
    val previous: String? = null
)


@Keep
data class LessonItem(

    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val time: String? = null,
    val pages: List<PageItem>? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(PageItem.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(title)
        writeString(description)
        writeString(time)
        writeTypedList(pages)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LessonItem> = object : Parcelable.Creator<LessonItem> {
            override fun createFromParcel(source: Parcel): LessonItem = LessonItem(source)
            override fun newArray(size: Int): Array<LessonItem?> = arrayOfNulls(size)
        }
    }
}


@Keep
data class PageItem(

    val id: Int? = null,
    val label: String? = null,
    val order: Int? = null,
    val type: Int? = null,
    val answers: List<AnswerItem>? = null,
    val is_answered: Boolean? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.createTypedArrayList(AnswerItem.CREATOR),
        source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(label)
        writeValue(order)
        writeValue(type)
        writeTypedList(answers)
        writeValue(is_answered)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PageItem> = object : Parcelable.Creator<PageItem> {
            override fun createFromParcel(source: Parcel): PageItem = PageItem(source)
            override fun newArray(size: Int): Array<PageItem?> = arrayOfNulls(size)
        }
    }
}


@Keep
data class AnswerItem(

    val id: Int? = null,
    val text: String? = null,
    val is_true: Boolean? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(text)
        writeValue(is_true)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AnswerItem> = object : Parcelable.Creator<AnswerItem> {
            override fun createFromParcel(source: Parcel): AnswerItem = AnswerItem(source)
            override fun newArray(size: Int): Array<AnswerItem?> = arrayOfNulls(size)
        }
    }
}


@Keep
data class ObjectDataResetPassword(
    val success: Boolean? = null,
    val message_ru: String? = null,
    val message_kz: String? = null
)

@Keep
data class ObjectData(
    val id: Int? = null,
    val name_ru: String? = null,
    val name_kz: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(name_ru)
        writeString(name_kz)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ObjectData> = object : Parcelable.Creator<ObjectData> {
            override fun createFromParcel(source: Parcel): ObjectData = ObjectData(source)
            override fun newArray(size: Int): Array<ObjectData?> = arrayOfNulls(size)
        }
    }
}


@Keep
data class ReviewSubmitResponse(

    val created: String? = null,
    val rating: String? = null,
    val id: Int? = null,
    val text: String? = null,
    val reviewer: Reviewer? = null,
    val createdInSec: Int? = null
)