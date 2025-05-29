package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.local.db.enties.GalleryImageEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.gallery.GalleryImage
import com.teqie.taskmaster.util.isoStringToLocalDate

object ImageEntityToDomainMapper: EntityToDomain<GalleryImageEntity, GalleryImage> {
    override fun GalleryImageEntity.toDomainModel(): GalleryImage {
        return GalleryImage(
            createdAt = createdAt.isoStringToLocalDate(),
            description = description,
            folderId = folderId,
            id = id,
            imageName = imageName,
            imageUrl = imageUrl,
            isDeleted = isDeleted == 1,
            projectId = projectId,
            tags = tags,
            updatedAt = updatedAt.isoStringToLocalDate()
        )
    }
}