package com.livefast.eattrash.rssgenerator.core.data

import com.livefast.eattrash.rssgenerator.core.data.converter.PostDtoToModelConverter
import com.livefast.eattrash.rssgenerator.core.model.PostData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.Date
import kotlin.test.assertEquals

class PostRepositoryImplTest {
    private val converter = mockk<PostDtoToModelConverter>()

    private val sut = PostRepositoryImpl(converter = converter)

    @Test
    fun `when getAll() is called, then it reads data from resources and converts them`() =
        runTest {
            val posts =
                listOf(
                    PostData(
                        title = "post 1",
                        lastSegment = "post-1-slug",
                        summary = "summary 1",
                        date = Date(),
                    ),
                    PostData(
                        title = "post 2",
                        lastSegment = "post-2-slug",
                        summary = "summary 2",
                        date = Date(),
                    ),
                )
            every { converter.convert(dto = any()) } returnsMany posts

            val result = sut.getAll()

            assertEquals(2, result.size)
            val post1 = result[0]
            assertEquals("post 1", post1.title)
            assertEquals("post-1-slug", post1.lastSegment)
            assertEquals("summary 1", post1.summary)
            val post2 = result[1]
            assertEquals("post 2", post2.title)
            assertEquals("post-2-slug", post2.lastSegment)
            assertEquals("summary 2", post2.summary)

            verify {
                converter.convert(
                    dto =
                        withArg {
                            assertEquals("post 1", it.title)
                            assertEquals("post-1-slug", it.lastSegment)
                            assertEquals("2026-06-29 12:00:00 +0200", it.date)
                            assertEquals("summary 1", it.summary)
                        },
                )
                converter.convert(
                    dto =
                        withArg {
                            assertEquals("post 2", it.title)
                            assertEquals("post-2-slug", it.lastSegment)
                            assertEquals("2026-06-27 12:00:00 +0200", it.date)
                            assertEquals("summary 2", it.summary)
                        },
                )
            }
        }
}
