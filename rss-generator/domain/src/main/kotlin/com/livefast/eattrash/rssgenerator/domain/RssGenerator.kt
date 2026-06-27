package com.livefast.eattrash.rssgenerator.domain

import com.livefast.eattrash.rssgenerator.core.model.PostData

/**
 * Use case responsible for generating an RSS feed from a list of blog posts.
 */
interface RssGenerator {
    /**
     * Transforms a list of [PostData] into a string representation of an RSS XML feed.
     *
     * @param posts list of blog posts to include in the feed
     * @return a formatted XML string containing the RSS feed
     */
    fun execute(posts: List<PostData>): String
}
