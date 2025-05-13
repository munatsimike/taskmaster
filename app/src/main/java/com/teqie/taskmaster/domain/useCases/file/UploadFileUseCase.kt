package com.teqie.taskmaster.domain.useCases.file

import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import com.teqie.taskmaster.domain.model.file.PresignedUrl
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(private val fileManagementRepo: FileManagementRepository) {
    operator fun invoke(file: File, presignedUrl: PresignedUrl): String {
        return  fileManagementRepo.uploadFileToPreSignedUrl(file, presignedUrl)
    }
}