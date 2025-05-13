package com.teqie.taskmaster.domain.useCases.file

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import com.teqie.taskmaster.domain.model.file.PresignedUrl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPresignedUrlUseCase @Inject constructor(private val fileManagementRepo: FileManagementRepository) {
    operator fun invoke(fileName: String, fileType: String): Flow<Resource<PresignedUrl>> =
        fileManagementRepo.getPreSignedUrl(fileName, fileType)
}