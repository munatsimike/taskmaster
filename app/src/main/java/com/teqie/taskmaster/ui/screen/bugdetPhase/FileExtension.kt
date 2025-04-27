package com.teqie.taskmaster.ui.screen.bugdetPhase

import com.teqie.taskmaster.ui.screen.bugdetPhase.FileExtension.entries

enum class FileExtension(val extension: String) {
    PDF("pdf"), DOC("doc"), DOCX("docx"), JPEG("jpeg"), JPG("jpg"), Excel("xlsx")
}

fun String.toFileExtension(): FileExtension? {
    return entries.find { it.extension.equals(this, ignoreCase = true) }
}