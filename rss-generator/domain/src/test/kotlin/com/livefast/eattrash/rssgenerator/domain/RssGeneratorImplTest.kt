package com.livefast.eattrash.rssgenerator.domain

import com.livefast.eattrash.rssgenerator.core.model.PostData
import com.livefast.eattrash.rssgenerator.core.utils.DateUtils
import io.mockk.every
import io.mockk.mockk
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class RssGeneratorImplTest {
    private val dateUtils = mockk<DateUtils>()

    private val sut = RssGeneratorImpl(dateUtils = dateUtils)

    @Test
    fun `when executed, then the RSS feed is generated correctly`() {
        val input =
            listOf(
                PostData(
                    title = "fake-title",
                    lastSegment = "fake-slug",
                    summary = "fake-summary",
                    date = Date(),
                ),
            )
        every { dateUtils.format(any()) } returns "2026-06-29 12:00:00 +0200"
        val expected =
            """<?xml version="1.0" encoding="UTF-8"?>
<rss xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
    <channel>
        <title>Procyon Project blog</title>
        <link>https://livefasteattrashraccoon.github.io/blog</link>
        <description>In the Procyon Project&apos;s blog we share updates about our Kotlin Multiplatform apps, development challenges, and thoughts on the Fediverse.</description>
        <language>en</language>
        <lastBuildDate>2026-06-29 12:00:00 +0200</lastBuildDate>
        <atom:link href="https://livefasteattrashraccoon.github.io/blog/rss.xml" ref="self" type="application/rss+xml"/>
        <image>
            <url>https://livefasteattrashraccoon.github.io/assets/logo.png</url>
            <title>Procyon Project blog</title>
            <link>https://livefasteattrashraccoon.github.io/blog</link>
        </image>
        <item>
            <title>fake-title</title>
            <link>https://livefasteattrashraccoon.github.io/blog/posts/fake-slug</link>
            <guid isPermalink="true">https://livefasteattrashraccoon.github.io/blog/posts/fake-slug</guid>
            <description>fake-summary</description>
            <pubDate>2026-06-29 12:00:00 +0200</pubDate>
        </item>
    </channel>
</rss>""".replace("    ", "\t")

        val result = sut.execute(input)

        assertEquals(expected, result)
    }
}
