package com.livefast.eattrash.rssgenerator.core.data.converter

import com.livefast.eattrash.rssgenerator.core.data.dto.PostMetadataDto
import com.livefast.eattrash.rssgenerator.core.model.PostData

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
