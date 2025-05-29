package com.teqie.taskmaster.data.mapper.orfi

import com.teqie.taskmaster.data.local.db.enties.OrfiFileEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.orfi.ORFIFile

object OrfiFileEntityToDomainMapper: EntityToDomain<OrfiFileEntity, ORFIFile> {
    override fun OrfiFileEntity.toDomainModel(): ORFIFile {
        return ORFIFile(
            orfiId = orfiId,
            fileType = FileType.ORFIFile,
            description = description,
            fileName = fileName,
            id = id,
            isDeleted = isDeleted == 1,
            url = url
        )
    }
}