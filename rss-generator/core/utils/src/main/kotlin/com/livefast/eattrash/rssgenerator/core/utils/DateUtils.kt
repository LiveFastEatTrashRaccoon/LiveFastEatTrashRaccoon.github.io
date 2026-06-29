package com.livefast.eattrash.rssgenerator.core.utils

import java.util.Date

interface DateUtils {
    fun normalized(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0,
    ): Date

    fun format(date: Date): String

    fun fromIso8601(date: String): Date?
}
