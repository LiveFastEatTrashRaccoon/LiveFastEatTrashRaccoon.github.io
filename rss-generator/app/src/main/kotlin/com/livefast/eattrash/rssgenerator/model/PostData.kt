package com.livefast.eattrash.rssgenerator.model

import java.util.Date

/**
 * Model to represent post items in the feed.
 */
data class PostData(
    val title: String,
    val lastSegment: String,
    val summary: String,
    val date: Date,
)
