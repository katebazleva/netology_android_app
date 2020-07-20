package ru.netology.kotlin.skynetwork

import java.time.YearMonth
import java.util.*

fun getPostedAgoHumanReadable(secondsAgo: Long): String {
    val minutesAgo = secondsAgo / 60
    val hoursAgo = minutesAgo / 60
    val daysAgo = hoursAgo / 24

    return when {
        daysAgo - getDaysInMonth() >= 0 -> getRightMonths(daysAgo.toInt())
        daysAgo > 0 -> getRightDays(daysAgo.toInt())
        hoursAgo > 0 -> getRightHours(hoursAgo.toInt())
        else -> getRightMinutes(minutesAgo.toInt())
    }
}

private fun getRightMinutes(minutes: Int) = when (minutes) {
    0 -> "менее минуты назад"
    1 -> "минуту назад"
    2, 3, 4 -> "$minutes минуты назад"
    else -> "$minutes минут назад"
}

private fun getRightHours(hours: Int) = when (hours) {
    1 -> "час назад"
    2, 3, 4, 22, 23 -> "$hours часа назад"
    21 -> "$hours час назад"
    else -> "$hours часов назад"
}

private fun getRightDays(days: Int) = when (days) {
    1 -> "день назад"
    2, 3, 4, 22, 23, 24 -> "$days дня назад"
    21 -> "$days день назад"
    else -> "$days дней назад"
}

private fun getRightMonths(days: Int): String {
    var monthAmount = -1
    var daysLeft = days
    var year = Date().year
    var previousMonthNumber = Date().month - 1

    while (daysLeft >= 0) {
        monthAmount++

        if (previousMonthNumber <= 0) {
            year -= 1
            previousMonthNumber = 12
        }
        daysLeft -= getDaysInMonth(year, YearMonth.of(year, previousMonthNumber).monthValue)

        previousMonthNumber -= 1
    }

    return when (monthAmount) {
        0, 1 -> "месяц назад"
        2, 3, 4 -> "$monthAmount месяца назад"
        in 5..11 -> "$monthAmount месяцев назад"
        in 12..23 -> "год назад"
        else -> "несколько лет назад"
    }
}

private fun getDaysInMonth(year: Int = Date().year, month: Int = Date().month - 1) =
    YearMonth.of(year, month).lengthOfMonth()
