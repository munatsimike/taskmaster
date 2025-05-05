package com.teqie.taskmaster.domain.useCases.file

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.file.FileManagementRepository
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(private val fileManagementRepository: FileManagementRepository) {
    operator fun invoke(fileId: String, fileType: FileType): Flow<Resource<ResponseMessage>>{
       return fileManagementRepository.deleteFile(fileId, fileType)
    }
}