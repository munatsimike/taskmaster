package com.teqie.taskmaster.domain.useCases.file

import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import javax.inject.Inject

class SaveFileUseCase @Inject constructor(private val fileManagementRepo: FileManagementRepository) {
    suspend operator fun invoke(fileData: FileData){
        fileManagementRepo.saveFile(fileData)
    }
}