package com.teqie.taskmaster.domain.useCases.gallery

import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import javax.inject.Inject

class DeleteFolderUseCase @Inject  constructor(private val galleryRepository: GalleryRepository){
}