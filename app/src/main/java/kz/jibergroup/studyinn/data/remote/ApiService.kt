package kz.jibergroup.studyinn.data.remote

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import kz.jibergroup.studyinn.domain.entity.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @FormUrlEncoded
    @POST("auth/registration/")
    fun registrationUser(
        @Field("password") password: String, @Field("first_name") first_name: String, @Field(
            "last_name"
        ) last_name: String, @Field("email", encoded = true) email: String
    ): Deferred<Response<AuthItem>>

    @FormUrlEncoded
    @POST("auth/login/")
    fun authUser(
        @Field(
            "email",
            encoded = true
        ) id: String, @Field("password") password: String
    ): Deferred<Response<AuthItem>>

    @GET("api/v1/posts/")
    fun getPosts(): Deferred<Response<PostResponse>>

    @FormUrlEncoded
    @POST("api/v1/course/join/")
    fun joinCourseToMyCourse(@Field("course_id") id: Int): Deferred<Response<GeneralResponse>>

    @GET("api/v1/courses/")
    fun getAllCourses(): Deferred<Response<CourseResponse>>

    @GET("api/v1/course/{id}/reviews/")
    fun getAllReviewsByCourseId(@Path("id") id: Int): Deferred<Response<ReviewResponse>>

    @GET("api/v1/course/{id}/modules/")
    fun getAllModulesByCourseId(@Path("id") id: Int): Deferred<Response<ModuleResponse>>

    @GET("api/v1/mycourses/")
    fun getAllMyCourses(): Deferred<Response<MyCoursesResponse>>

    @GET("api/v1/course/categories/")
    fun getCourseCategories(): Deferred<Response<MutableList<ObjectData>>>

    @GET("api/v1/courses/")
    fun getCategoryCourses(@Query("category_id") id: String): Deferred<Response<CourseResponse>>

    @GET("api/v1/course/modules/{module_id}/lessons/{lesson_id}/")
    fun getCourseLessonPages(@Path("module_id") module_id: Int, @Path("lesson_id") lesson_id: Int): Deferred<Response<LessonItem>>

    @FormUrlEncoded
    @POST("user/change-password/")
    fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String
    ): Deferred<Response<ResponseServer>>

    @FormUrlEncoded
    @POST("user/change-email/")
    fun changeEmail(
        @Field("email", encoded = true) email: String
    ): Deferred<Response<ResponseServer>>

    @POST("auth/logout/")
    fun logout(): Deferred<Response<ResponseBody>>

    @FormUrlEncoded
    @POST("user/reset-password/")
    fun restorePassword(@Field("email") email: String): Deferred<Response<ObjectDataResetPassword>>

    @Multipart
    @PUT("user/update/")
    fun savePhoto(@Part images: MultipartBody.Part): Deferred<Response<ProfileItem>>

    @FormUrlEncoded
    @PUT("user/update/")
    fun editProfile(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String
    ): Deferred<Response<ProfileItem>>

    @FormUrlEncoded
    @POST("api/v1/course/{id}/reviews/")
    fun submitReview(
        @Path("id") id: Int,
        @Field("text") text: String,
        @Field("rating") rating: String
    ) : Deferred<Response<ReviewSubmitResponse>>

}

