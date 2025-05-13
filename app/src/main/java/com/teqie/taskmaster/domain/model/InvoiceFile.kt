package com.teqie.taskmaster.domain.model

import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.file.MyFile

data class InvoiceFile(
    val createdAt: String = "",
    val invoiceId: String= "",
    val updatedAt: String= "",
    override  val fileType: FileType = FileType.InvoiceFile,
    override val description: String= "",
    override val fileName: String= "",
    override val id: String= "",
    override val isDeleted: Boolean= false,
    override val url: String = ""
) : MyFile(
    fileType,
    description,
    fileName,
    id,
    isDeleted,
    url
)