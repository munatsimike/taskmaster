package com.teqie.taskmaster.domain.useCases.gallery

import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import com.teqie.taskmaster.domain.gallery.FolderState
import javax.inject.Inject

class AddFolderUseCase @Inject constructor(private val galleryRepository: GalleryRepository) {
   suspend operator fun invoke (folderState: FolderState){
       galleryRepository.addFolder(folderState)
   }
}