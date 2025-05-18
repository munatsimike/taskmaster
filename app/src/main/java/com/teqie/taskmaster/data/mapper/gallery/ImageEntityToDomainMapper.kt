package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.local.db.enties.GalleryImageEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.gallery.GalleryImage

object ImageEntityToDomainMapper: EntityToDomain<GalleryImageEntity, GalleryImage> {
    override fun GalleryImageEntity.toDomainModel(): GalleryImage {
        return GalleryImage(
            createdAt = TODO(),
            description = TODO(),
            folderId = TODO(),
            id = TODO(),
            imageName = TODO(),
            imageUrl = TODO(),
            isDeleted = TODO(),
            projectId = TODO(),
            tags = TODO(),
            updatedAt = TODO()
        )
    }
}