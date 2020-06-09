package kz.jibergroup.studyinn.presentation.image_dialog

import android.os.Parcel
import android.os.Parcelable

data class ImageItem(
    val id: Int? = 0,
    val link: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(link)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImageItem> = object : Parcelable.Creator<ImageItem> {
            override fun createFromParcel(source: Parcel): ImageItem = ImageItem(source)
            override fun newArray(size: Int): Array<ImageItem?> = arrayOfNulls(size)
        }
    }
}