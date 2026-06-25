package com.livefast.eattrash.rssgenerator.core

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

interface DateUtils {
    fun normalized(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0
    ): Date

    fun format(date: Date): String

    fun fromIso8601(date: String): Date
}

class DateUtilsImpl : DateUtils {

    private val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US)
    private val iso8601DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZ", Locale.US)

    override fun normalized(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int,
    ): Date {
        Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("Europe/Rome")
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, (month - 1).coerceIn(0, 11))
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
            set(Calendar.MILLISECOND, 0)
            return time
        }
    }

    override fun format(date: Date): String {
        return dateFormat.format(date)
    }

    override fun fromIso8601(date: String): Date {
        return iso8601DateFormat.parse(date)
    }
}
