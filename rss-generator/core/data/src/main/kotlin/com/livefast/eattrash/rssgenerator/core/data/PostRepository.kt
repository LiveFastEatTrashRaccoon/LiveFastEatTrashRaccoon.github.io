package com.livefast.eattrash.rssgenerator.core.data

import com.livefast.eattrash.rssgenerator.core.model.PostData

/**
 * Repository for post data.
 */
interface PostRepository {
    /**
     * Get all post data.
     */
    fun getAll(): List<PostData>
}
