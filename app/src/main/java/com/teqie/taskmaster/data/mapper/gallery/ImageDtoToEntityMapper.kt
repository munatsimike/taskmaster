package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.local.db.enties.GalleryImageEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.gallery.ImageResponseDto

object ImageDtoToEntityMapper : DtoToEntityMapper<ImageResponseDto, GalleryImageEntity> {
    override fun ImageResponseDto.toEntity(projectId: String?): GalleryImageEntity {
        return GalleryImageEntity(
            currentPage = currentPage,
            images = images,
            totalItems = totalItems,
            totalPages = totalPages
        )
    }
}