package com.teqie.taskmaster.domain.model.file

open class MyFile(
    open val fileType: FileType,
    open val description: String,
    open val fileName: String,
    open val id: String,
    open val isDeleted: Boolean,
    open val url: String
)