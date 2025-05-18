package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.local.db.enties.FolderEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.gallery.Folder

object FolderEntityToDomainMapper: EntityToDomain<FolderEntity, Folder>{
    override fun FolderEntity.toDomainModel(): Folder {
        return Folder(
            description = description,
            id = id,
            isDeleted = isDeleted,
            name = name,
            projectId = projectId
        )
    }
}