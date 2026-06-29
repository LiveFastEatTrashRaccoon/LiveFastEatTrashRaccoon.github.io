package com.livefast.eattrash.rssgenerator.core.utils

import java.util.Calendar
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DateUtilsImplTest {
    private val sut = DateUtilsImpl()

    @Test
    fun `when normalized is called, then it returns the correct date`() {
        val result = sut.normalized(year = 2026, month = 6, day = 29, hour = 12)

        val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"))
        cal.time = result
        assertEquals(2026, cal.get(Calendar.YEAR))
        assertEquals(5, cal.get(Calendar.MONTH))
        assertEquals(29, cal.get(Calendar.DAY_OF_MONTH))
        assertEquals(12, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
        assertEquals(0, cal.get(Calendar.SECOND))
    }

    @Test
    fun `when format is called, then it prints the date in the correct format`() {
        val date =
            Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome")).run {
                set(Calendar.YEAR, 2026)
                set(Calendar.MONTH, 5)
                set(Calendar.DAY_OF_MONTH, 29)
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                time
            }

        val result = sut.format(date)

        assertEquals("Mon, 29 Jun 2026 12:00:00 +0200", result)
    }

    @Test
    fun `given a valid string, when fromIso8601 is called, then it returns the correct date`() {
        val result = sut.fromIso8601("2026-06-29 12:00:00 +0200")

        val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"))
        cal.time = result
        assertEquals(2026, cal.get(Calendar.YEAR))
        assertEquals(5, cal.get(Calendar.MONTH))
        assertEquals(29, cal.get(Calendar.DAY_OF_MONTH))
        assertEquals(12, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
        assertEquals(0, cal.get(Calendar.SECOND))
    }

    @Test
    fun `given an invalid string, when fromIso8601 is called, then it returns null`() {
        val result = sut.fromIso8601("2026-xx-yy-zz")

        assertNull(result)
    }
}
