package com.livefast.eattrash.rssgenerator.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal class DateUtilsImpl : DateUtils {
    private val timeZone = TimeZone.getTimeZone("Europe/Rome")

    private val dateFormat =
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US).apply {
            timeZone = this@DateUtilsImpl.timeZone
        }

    private val iso8601DateFormat =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZ", Locale.US).apply {
            timeZone = this@DateUtilsImpl.timeZone
        }

    override fun normalized(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int,
    ): Date {
        Calendar.getInstance(timeZone).apply {
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

    override fun format(date: Date): String = dateFormat.format(date)

    override fun fromIso8601(date: String): Date? =
        try {
            iso8601DateFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
}
