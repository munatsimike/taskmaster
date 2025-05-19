package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.local.db.enties.GalleryImageEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.gallery.GalleryImageDto

object ImageDtoToEntityMapper : DtoToEntityMapper<GalleryImageDto, GalleryImageEntity> {
    override fun GalleryImageDto.toEntity(projectId: String?): GalleryImageEntity {
        return GalleryImageEntity(
            createdAt = createdAt,
            description = description,
            imageName = imageName,
            imageUrl = imageUrl,
            isDeleted = isDeleted,
            projectId = this.projectId,
            tags = tags,
            updatedAt = updatedAt,
            folderId = folderId,
            id = id
        )
    }
}