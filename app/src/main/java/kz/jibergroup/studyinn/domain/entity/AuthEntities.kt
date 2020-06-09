package kz.jibergroup.studyinn.domain.entity

import androidx.annotation.Keep

@Keep
data class ResponseServer(
    val success: Boolean? = null,
    val message: String? = null
)

@Keep
data class AuthItem(
    val token: String? = null,
    val success: Boolean? = null,
    val profile: ProfileItem? = null
)

@Keep
data class ProfileItem(
    val id: Int? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val image: Any? = null

)

@Keep
data class AuthResponse(

    val next: String? = null,
    val previous: Any? = null,
    val count: Int? = null,
    val results: AuthItem? = null
)