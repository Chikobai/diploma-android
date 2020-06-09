package kz.jibergroup.studyinn.presentation.home

import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.PostItem

data class PostItem(
    val author : String? = null,
    val title : String? = null,
    val description : String? = null,
    val publishDate : String? = null
)


interface ItemClickListener{
    fun click(position : Int)
}


fun getShimmerData() : MutableList<PostItem>{

    val data = mutableListOf<PostItem>()

    data.add(PostItem(dataInitType = DataInitType.SHIMMER))
    data.add(PostItem(dataInitType = DataInitType.SHIMMER))
    data.add(PostItem(dataInitType = DataInitType.SHIMMER))
    data.add(PostItem(dataInitType = DataInitType.SHIMMER))
    data.add(PostItem(dataInitType = DataInitType.SHIMMER))


    return data

}