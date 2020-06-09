package kz.jibergroup.studyinn.presentation.profile

data class NotificationItem(
    val id: Int? = null,
    val start_time: String? = null,
    val end_time: String? = null
)

data class UserLocalNotificationItem(
    val enabled: Boolean? = null,
    val time : String? = null
)