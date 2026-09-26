package com.livefast.eattrash.rssgenerator.core.data.converter

import com.livefast.eattrash.rssgenerator.core.data.dto.PostMetadataDto
import com.livefast.eattrash.rssgenerator.core.model.PostData
import com.livefast.eattrash.rssgenerator.core.utils.DateUtils
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject

@ContributesBinding(AppScope::class)
@Inject
class PostDtoToModelConverterImpl(
    private val dateUtils: DateUtils,
) : PostDtoToModelConverter {
    override fun convert(dto: PostMetadataDto): PostData =
        PostData(
            title = dto.title,
            lastSegment = dto.lastSegment,
            summary = dto.summary,
            date = dateUtils.fromIso8601(dto.date),
        )
}
