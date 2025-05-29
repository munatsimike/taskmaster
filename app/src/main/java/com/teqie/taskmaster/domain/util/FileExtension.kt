package com.teqie.taskmaster.domain.util

import com.teqie.taskmaster.domain.util.FileExtension.entries

enum class FileExtension(val extension: String) {
    PDF("pdf"), DOC("doc"), DOCX("docx"), JPEG("jpeg"), JPG("jpg"), Excel("xlsx")
}

fun String.toFileExtension(): FileExtension? {
    return entries.find { it.extension.equals(this, ignoreCase = true) }
}

fun String.getFileExtension(): String? {
    val extension = this.substringAfterLast('.', "").lowercase()
    val extensions = listOf("pdf", "doc", "jpeg", "jpg", "docx", "xlsx")
    return if (extensions.contains(extension)) {
        extension
    } else {
        null
    }
}