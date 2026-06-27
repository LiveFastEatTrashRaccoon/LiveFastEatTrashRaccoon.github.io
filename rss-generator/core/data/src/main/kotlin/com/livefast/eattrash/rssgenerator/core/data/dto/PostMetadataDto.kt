package com.livefast.eattrash.rssgenerator.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostMetadataDto(
    val title: String,
    val date: String,
    val lastSegment: String,
    val summary: String,
)
