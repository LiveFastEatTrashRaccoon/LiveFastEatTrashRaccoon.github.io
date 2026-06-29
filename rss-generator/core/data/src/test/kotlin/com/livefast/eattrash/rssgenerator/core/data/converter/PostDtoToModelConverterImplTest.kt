package com.livefast.eattrash.rssgenerator.core.data.converter

import com.livefast.eattrash.rssgenerator.core.data.dto.PostMetadataDto
import com.livefast.eattrash.rssgenerator.core.utils.DateUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.matcher.ElementMatchers.any
import java.util.Calendar
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class PostDtoToModelConverterImplTest {
    private val dateUtils = mockk<DateUtils>()

    private val sut = PostDtoToModelConverterImpl(dateUtils = dateUtils)

    @Test
    fun `when convert is called, then it returns the expected model`() {
        val input =
            PostMetadataDto(
                title = "fake-title",
                lastSegment = "fake-last-segment",
                date = "2026-06-29 12:00:00 +0200",
                summary = "fake-summary",
            )
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
        every { dateUtils.fromIso8601(date = any()) } returns date

        val result = sut.convert(input)

        assertEquals(input.title, result.title)
        assertEquals(input.lastSegment, result.lastSegment)
        assertEquals(input.summary, result.summary)
        assertEquals(date, result.date)

        verify {
            dateUtils.fromIso8601(input.date)
        }
    }
}
