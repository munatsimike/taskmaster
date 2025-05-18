package com.teqie.taskmaster.domain.useCases.gallery

import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import com.teqie.taskmaster.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncImageToLocalDbUseCase @Inject constructor(private val galleryRepository: GalleryRepository){
    operator  fun invoke(projectId: String): Flow<Resource<Unit>> {
        return galleryRepository.syncImagesToLocal(projectId)
    }
}