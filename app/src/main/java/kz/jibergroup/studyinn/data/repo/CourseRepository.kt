package kz.jibergroup.studyinn.data.repo

import android.content.Context
import kz.jibergroup.studyinn.data.pref.PrefManager
import kz.jibergroup.studyinn.data.remote.ApiService
import kz.jibergroup.studyinn.domain.ApiCaller
import kz.jibergroup.studyinn.domain.CoroutineCaller

class CourseRepository(
    private val context: Context,
    private val api: ApiService,
    private val prefManager: PrefManager
) : CoroutineCaller by ApiCaller() {

    suspend fun joinCourseToMyCourse(courseId: Int) =
        coroutineApiCall(api.joinCourseToMyCourse(courseId))

    suspend fun getPosts() = coroutineApiCall(api.getPosts())

    suspend fun getAllCourses() = coroutineApiCall(api.getAllCourses())

    suspend fun getAllReviewsByCourseId(id: Int) = coroutineApiCall(api.getAllReviewsByCourseId(id))

    suspend fun getAllModulesByCourseId(id: Int) = coroutineApiCall(api.getAllModulesByCourseId(id))

    suspend fun getAllMyCourses() = coroutineApiCall(api.getAllMyCourses())

    suspend fun getCourseLessonPages(module_id: Int, lesson_id: Int) =
        coroutineApiCall(api.getCourseLessonPages(module_id, lesson_id))

    suspend fun getCourseCategories() = coroutineApiCall(api.getCourseCategories())

    suspend fun getCourseCategories(id: String) = coroutineApiCall(api.getCategoryCourses(id))

    suspend fun submitReview(id: Int, text: String, rating: String) =
        coroutineApiCall(api.submitReview(id, text, rating))
}