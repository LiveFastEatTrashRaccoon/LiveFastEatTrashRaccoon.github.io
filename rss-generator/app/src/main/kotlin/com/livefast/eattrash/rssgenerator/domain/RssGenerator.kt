package com.livefast.eattrash.rssgenerator.domain

import com.livefast.eattrash.rssgenerator.core.DateUtils
import com.livefast.eattrash.rssgenerator.model.PostData
import org.redundent.kotlin.xml.xml
import java.util.Date

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

class RssGeneratorImpl(
    private val dateUtils: DateUtils
) : RssGenerator {

    override fun execute(posts: List<PostData>): String {
        val lastDate = posts.firstOrNull()?.date ?: Date()

        val root = xml("rss") {
            includeXmlProlog = true
            attribute("version", "2.0")
            namespace("atom", "http://www.w3.org/2005/Atom")

            "channel" {
                "title" {
                    -"Procyon Project blog"
                }
                "link" {
                    -BLOG_URL
                }
                "description" {
                    -"In the Procyon Project's blog we share updates about our Kotlin Multiplatform apps, development challenges, and thoughts on the Fediverse."
                }
                "language" {
                    -"en"
                }
                "lastBuildDate" {
                    -dateUtils.format(lastDate)
                }
                "atom:link" {
                    attribute("href", "$BLOG_URL/rss.xml")
                    attribute("ref", "self")
                    attribute("type", "application/rss+xml")
                }

                "image" {
                    "url" {
                        -"$BASE_URL/assets/logo.png"
                    }
                    "title" {
                        -"Procyon Project blog"
                    }
                    "link" {
                        -BLOG_URL
                    }
                }

                for (post in posts) {
                    "item" {
                        "title" {
                            -post.title
                        }
                        "link" {
                            -"$POST_URL/${post.lastSegment}"
                        }
                        "guid" {
                            attribute("isPermalink", true)
                            -"$POST_URL/${post.lastSegment}"
                        }
                        "description" {
                            -post.summary
                        }
                        "pubDate" {
                            -dateUtils.format(post.date)
                        }
                    }
                }
            }
        }

        val result = root
            .toString(prettyFormat = true)
            .replace(Regex(">\\s*([^<\\s][^<]*?)\\s*<"), ">$1<")
        return result
    }

    companion object {
        const val BASE_URL = "https://livefasteattrashraccoon.github.io"
        const val BLOG_URL = "$BASE_URL/blog"
        const val POST_URL = "$BLOG_URL/posts"
    }
}
