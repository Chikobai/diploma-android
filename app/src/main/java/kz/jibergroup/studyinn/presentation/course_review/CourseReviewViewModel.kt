package kz.jibergroup.studyinn.presentation.course_review

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.DataInitType
import kz.jibergroup.studyinn.domain.entity.ReviewItem

class CourseReviewViewModel(
    private val personalRepository: PersonalRepository,
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    val reviews = MutableLiveData<MutableList<ReviewItem>>()

    fun getReviews(id: Int) {
        makeRequest({ courseRepository.getAllReviewsByCourseId(id) }) {
            unwrap(it) {
                reviews.value = it.data
            }
        }
    }


    fun initShimmerData() {
        val data = mutableListOf<ReviewItem>()

        data.add(ReviewItem(dateInitType = DataInitType.SHIMMER))
        data.add(ReviewItem(dateInitType = DataInitType.SHIMMER))
        data.add(ReviewItem(dateInitType = DataInitType.SHIMMER))
        data.add(ReviewItem(dateInitType = DataInitType.SHIMMER))
        data.add(ReviewItem(dateInitType = DataInitType.SHIMMER))
        data.add(ReviewItem(dateInitType = DataInitType.SHIMMER))

        reviews.value = data
    }

}
