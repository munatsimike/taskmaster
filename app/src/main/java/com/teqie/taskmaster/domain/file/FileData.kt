package com.teqie.taskmaster.domain.file

import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.util.FileExtension
import com.teqie.taskmaster.domain.util.getFileExtension
import com.teqie.taskmaster.domain.util.toFileExtension


data class FileData(
    val projectId: String = "",
    val tags: List<String> = listOf("red", "blue", "green"),
    override val fileType: FileType = FileType.UNKNOWN,
    override val description: String = "",
    override val fileName: String = "",
    override val id: String = "",
    override val isDeleted: Boolean = false,
    override val url: String = "",
) : MyFile(fileType, description, fileName, id, isDeleted, url) {

    fun isFileValid(): Boolean {
        val fileExtension = url.getFileExtension()?.toFileExtension()
        return when (fileType) {
            FileType.InvoiceFile, FileType.ORFIFile -> {
                fileExtension in listOf(FileExtension.PDF, FileExtension.DOC, FileExtension.DOCX)
            }

            FileType.IMAGE -> {
                //fileExtension in FileExtension.JPEG.extensions.map { it.lowercase() }
                true
            }

            FileType.UNKNOWN -> {
                false
            }
        }
    }
}