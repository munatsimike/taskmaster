package com.teqie.taskmaster.domain.model.orfi

import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.file.MyFile


class ORFIFile(
    val orfiId: String,
    override val fileType: FileType = FileType.ORFIFile,
    override val description: String,
    override val fileName: String,
    override val id: String,
    override val isDeleted: Boolean,
    override val url: String
) : MyFile(
    fileType,
    description,
    fileName,
    id,
    isDeleted,
    url
)