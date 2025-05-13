package com.teqie.taskmaster.domain.mapper

import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.file.MyFile

object Mapper {
    fun MyFile.toCommonFile(): FileData {
        return when (fileType) {
            FileType.InvoiceFile -> {
                FileData(
                    id = id,
                    fileName = fileName,
                    description = description,
                    url = url
                )
            }

            FileType.ORFIFile -> {
                FileData(
                    id = id,
                    fileName = fileName,
                    description = description,

                )
            }
            FileType.IMAGE -> {
                FileData(
                    id = id,
                    fileName = fileName,
                )
            }

            FileType.UNKNOWN -> (FileData())
        }
    }
}