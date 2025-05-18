package com.teqie.taskmaster.domain.useCases.gallery

import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetFoldersUseCase @Inject constructor( private val galleryRepository: GalleryRepository){
    operator  fun invoke(projectId: String): Flow<Resource<List<Folder>>> {
        return galleryRepository.getProjectFolders(projectId)
    }
}