package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.core.data.PostRepository
import com.livefast.eattrash.rssgenerator.core.model.PostData
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AppImplTest {
    private val postRepository = mockk<PostRepository>()
    private val rssGenerator = mockk<RssGenerator>()
    private val filePrinter = mockk<FilePrinter>()

    private lateinit var sut: App

    @Test
    fun `when run is invoked, then it executes the logic`() =
        runTest {
            sut =
                AppImpl(
                    repository = postRepository,
                    generator = rssGenerator,
                    printer = filePrinter,
                )
            val data = listOf<PostData>()
            val content = "fake-content"
            coEvery { postRepository.getAll() } returns data
            every { rssGenerator.execute(any()) } returns content
            coEvery { filePrinter.execute(any()) } just runs

            sut.run()

            coVerify {
                postRepository.getAll()
                rssGenerator.execute(data)
                filePrinter.execute(content)
            }
        }
}
