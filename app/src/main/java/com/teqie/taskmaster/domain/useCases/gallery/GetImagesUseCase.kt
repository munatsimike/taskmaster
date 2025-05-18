package com.teqie.taskmaster.domain.useCases.gallery

import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.domain.gallery.GalleryImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val galleryRepository: GalleryRepository){
    operator  fun invoke(folderId: String): Flow<Resource<List<GalleryImage>>> {
        return galleryRepository.getGalleryImages(folderId)
    }
}