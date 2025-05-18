package com.teqie.taskmaster.domain.useCases.gallery

import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFolderUseCase @Inject  constructor(private val galleryRepository: GalleryRepository){
    operator fun invoke(folderId: String): Flow<Resource<ResponseMessage>>{
       return galleryRepository.deleteFolder(folderId)
    }
}