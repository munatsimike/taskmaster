package com.teqie.taskmaster.domain.useCases.file

import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import com.teqie.taskmaster.domain.util.FileExtension
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor(private val fileManagementRepository: FileManagementRepository) {
    suspend operator fun invoke(fileUrl: String, fileName: String, fileType: FileExtension, progress: (Int)-> Unit) {
        fileManagementRepository.downloadFile(fileUrl, fileName, fileType, progress)
    }
}