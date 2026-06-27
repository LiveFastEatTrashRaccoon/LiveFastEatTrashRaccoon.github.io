package com.livefast.eattrash.rssgenerator.core.data

import com.livefast.eattrash.rssgenerator.core.data.converter.PostDtoToModelConverter
import com.livefast.eattrash.rssgenerator.core.data.dto.MetadataRootDto
import com.livefast.eattrash.rssgenerator.core.model.PostData
import net.mamoe.yamlkt.Yaml

internal class PostRepositoryImpl(
    private val converter: PostDtoToModelConverter,
) : PostRepository {
    override fun getAll(): List<PostData> {
        val root =
            PostRepositoryImpl::class.java
                .getResourceAsStream(RESOURCE_PATH)
                ?.use { stream ->
                    val content = stream.readAllBytes().toString(charset = Charsets.UTF_8)
                    Yaml.decodeFromString(deserializer = MetadataRootDto.serializer(), string = content)
                }
        val result = root?.metadata?.map { dto -> converter.convert(dto) }
        return result.orEmpty()
    }

    companion object {
        private const val RESOURCE_PATH = "/metadata.yml"
    }
}
