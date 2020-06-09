package kz.jibergroup.studyinn.domain.entity

data class VoWrapper<out T>(
    val data: T?,
    val status: Boolean?,
    val detail: String?
)

data class StatusMessage(
    val message: String? = null,
    val code: Int
)