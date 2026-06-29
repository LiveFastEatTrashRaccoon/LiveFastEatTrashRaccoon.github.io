package com.livefast.eattrash.rssgenerator.domain

import com.livefast.eattrash.rssgenerator.core.model.PostData
import com.livefast.eattrash.rssgenerator.core.utils.DateUtils
import org.redundent.kotlin.xml.xml
import java.util.Date

internal class RssGeneratorImpl(
    private val dateUtils: DateUtils,
) : RssGenerator {
    override fun execute(posts: List<PostData>): String {
        val lastDate = posts.firstOrNull()?.date ?: Date()

        val root =
            xml("rss") {
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
                        -buildString {
                            append("In the Procyon Project's blog we share updates about our ")
                            append("Kotlin Multiplatform apps, development challenges, ")
                            append("and thoughts on the Fediverse.")
                        }
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
                            post.date?.let { date ->
                                "pubDate" {
                                    -dateUtils.format(date)
                                }
                            }
                        }
                    }
                }
            }

        val result =
            root
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
