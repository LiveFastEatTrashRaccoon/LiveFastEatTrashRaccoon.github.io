package com.livefast.eattrash.rssgenerator.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetadataRootDto(
    val metadata: List<PostMetadataDto>,
)
