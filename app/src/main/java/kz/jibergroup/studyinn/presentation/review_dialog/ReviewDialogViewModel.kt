package kz.jibergroup.studyinn.presentation.review_dialog

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.domain.entity.ReviewSubmitResponse

class ReviewDialogViewModel(
    private val personalRepository: PersonalRepository,
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    val courseId = MutableLiveData<Int>()
    val userData = MutableLiveData<ProfileItem>()
    val response = MutableLiveData<ReviewSubmitResponse>()

    fun getUserData() {
        userData.value = personalRepository.getUserInfo()
    }

    fun submitReview(rating: Float, comment: String) {
        makeRequest({
            courseRepository.submitReview(
                courseId.value ?: -1,
                comment,
                rating.toString()
            )
        }) {
            unwrap(it) {
                response.value = it as ReviewSubmitResponse
            }
        }
    }

}
