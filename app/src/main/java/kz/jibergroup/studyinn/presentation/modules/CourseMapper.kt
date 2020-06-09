package kz.jibergroup.studyinn.presentation.modules

import android.os.Parcel
import android.os.Parcelable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class ModuleItem(
    val id : Int?, val titleParent: String?, val modules: MutableList<ModuleChildItem>?
) : ExpandableGroup<ModuleChildItem>(titleParent, modules)

data class ModuleChildItem(
    var id: Int? = null,
    var title: String? = null,
    var points: Int? = null,
    var duration: String? = null,
    var pupilCount: Int? = null,
    var likeCount: Int? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(title)
        writeValue(points)
        writeString(duration)
        writeValue(pupilCount)
        writeValue(likeCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ModuleChildItem> =
            object : Parcelable.Creator<ModuleChildItem> {
                override fun createFromParcel(source: Parcel): ModuleChildItem =
                    ModuleChildItem(source)

                override fun newArray(size: Int): Array<ModuleChildItem?> = arrayOfNulls(size)
            }
    }
}