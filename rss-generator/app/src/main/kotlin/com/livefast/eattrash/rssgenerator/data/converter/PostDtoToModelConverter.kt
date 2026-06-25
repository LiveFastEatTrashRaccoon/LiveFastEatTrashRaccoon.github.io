package com.livefast.eattrash.rssgenerator.data.converter

import com.livefast.eattrash.rssgenerator.core.DateUtils
import com.livefast.eattrash.rssgenerator.data.dto.PostMetadataDto
import com.livefast.eattrash.rssgenerator.model.PostData

/**
 * Converter from [PostMetadataDto] to [PostData].
 */
interface PostDtoToModelConverter {
    /**
     * Converts from a [PostMetadataDto] to a [PostData] model.
     *
     * @param dto [PostMetadataDto] to convert
     * @return converted [PostData]
     */
    fun convert(dto: PostMetadataDto): PostData
}

class PostDtoToModelConverterImpl(
    private val dateUtils: DateUtils
) : PostDtoToModelConverter {
    override fun convert(dto: PostMetadataDto): PostData {
        return PostData(
            title = dto.title,
            lastSegment = dto.lastSegment,
            summary = dto.summary,
            date = dateUtils.fromIso8601(dto.date)
        )
    }
}
