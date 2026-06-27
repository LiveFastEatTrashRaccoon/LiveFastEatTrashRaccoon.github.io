package com.livefast.eattrash.rssgenerator.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetadataRootDto(
    val metadata: List<PostMetadataDto>,
)
