package com.teqie.taskmaster.domain.useCases.file

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditFileUseCase @Inject constructor(private val fileManagementRepo: FileManagementRepository) {
    operator fun invoke(fileData: FileData): Flow<Resource<ResponseMessage>> {
        return   fileManagementRepo.editFile(fileData)
    }
}