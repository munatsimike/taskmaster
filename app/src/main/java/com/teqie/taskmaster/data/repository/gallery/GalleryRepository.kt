package com.teqie.taskmaster.data.repository.gallery

import com.teqie.taskmaster.data.remote.dto.gallery.SaveImageRequestDto
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.domain.gallery.FolderState
import com.teqie.taskmaster.domain.gallery.GalleryImage
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    fun getGalleryImages(folderId: String): Flow<Resource<List<GalleryImage>>>
    suspend fun addFolder(folderState: FolderState)
    fun deleteFolder(folderId: String) : Flow<Resource<ResponseMessage>>
    fun getProjectFolders(projectId: String): Flow<Resource<List<Folder>>>
    fun syncFoldersToLocal(projectId: String): Flow<Resource<Unit>>
    fun syncImagesToLocal(projectId: String): Flow<Resource<Unit>>
    suspend fun saveImage(saveImageRequestDto: SaveImageRequestDto)
}