package com.teqie.taskmaster.domain.file

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.util.FileExtension
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileManagementRepository {
    suspend fun downloadFile(
        fileUrl: String,
        fileName: String,
        fileType: FileExtension,
        progress: (Int) -> Unit
    )

    fun getPreSignedUrl(fileName: String, fileType: String): Flow<Resource<PresignedUrl>>
    fun uploadFileToPreSignedUrl(file: File, preSignedUrl: PresignedUrl): String
    fun deleteFile(fileId: String, fileType: FileType): Flow<Resource<ResponseMessage>>
}