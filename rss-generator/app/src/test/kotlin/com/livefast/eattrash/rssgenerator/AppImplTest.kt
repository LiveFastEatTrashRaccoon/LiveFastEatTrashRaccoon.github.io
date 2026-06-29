package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.core.data.PostRepository
import com.livefast.eattrash.rssgenerator.core.model.PostData
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlin.test.Test

class AppImplTest {
    private val postRepository = mockk<PostRepository>()
    private val rssGenerator = mockk<RssGenerator>()
    private val filePrinter = mockk<FilePrinter>()

    private val sut =
        AppImpl(
            repository = postRepository,
            generator = rssGenerator,
            printer = filePrinter,
        )

    @Test
    fun `when run is invoked, then it executes the logic`() {
        val data = listOf<PostData>()
        val content = "fake-content"
        every { postRepository.getAll() } returns data
        every { rssGenerator.execute(any()) } returns content
        every { filePrinter.execute(any()) } just runs

        sut.run()

        verify {
            postRepository.getAll()
            rssGenerator.execute(data)
            filePrinter.execute(content)
        }
    }
}
