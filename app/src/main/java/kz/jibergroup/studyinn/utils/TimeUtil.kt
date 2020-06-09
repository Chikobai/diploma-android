package kz.jibergroup.studyinn.utils

import java.util.*


const val DATE_FORMAT = "MM/dd/yyyy"

fun returnMonthTitleFromLongMillis(timeLong: Long): String {

    val cal = Calendar.getInstance()
    cal.timeInMillis = timeLong
    val d = cal.time as Date

    val time = d.month.toString().plus(" ").plus(returnDateMonthTitle(d.month))

    return time

}

private fun returnDateMonthTitle(month: Int): String {

    val monthMap = mapOf(
        0 to MONTH.JANUARY,
        1 to MONTH.FEBRUARY,
        2 to MONTH.MARCH,
        3 to MONTH.APRIL,
        4 to MONTH.MAY,
        5 to MONTH.JUNE,
        6 to MONTH.JULY,
        7 to MONTH.AUGUST,
        8 to MONTH.SEPTEMBER,
        9 to MONTH.OCTOBER,
        10 to MONTH.NOVEMBER,
        11 to MONTH.DECEMBER
    )

    val mapCiyTitle = mapOf(
        MONTH.JANUARY to "января",
        MONTH.FEBRUARY to "февраля",
        MONTH.MARCH to "марта",
        MONTH.APRIL to "апреля",
        MONTH.MAY to "май",
        MONTH.JUNE to "июнь",
        MONTH.JULY to "июль",
        MONTH.AUGUST to "августа",
        MONTH.SEPTEMBER to "сентября",
        MONTH.OCTOBER to "октября",
        MONTH.NOVEMBER to "ноября",
        MONTH.DECEMBER to "декабря"
    )

    return mapCiyTitle.get(monthMap.get(month)) ?: ""
}

enum class MONTH {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
}

