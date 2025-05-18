package com.teqie.taskmaster.data.repository.gallery

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.gallery.FolderDomainToDtoMapper.toDtoModel
import com.teqie.taskmaster.data.mapper.gallery.FolderDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.gallery.FolderEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.mapper.gallery.ImageDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.gallery.ImageEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.budget.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.domain.gallery.FolderState
import com.teqie.taskmaster.domain.gallery.GalleryImage
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : BaseRepository(), GalleryRepository {

    override fun getGalleryImages(folderId: String): Flow<Resource<List<GalleryImage>>> =flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.fetchImages(folderId) },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    override suspend fun addFolder(folderState: FolderState) {
        remoteDataSource.addFolder(folderState.toDtoModel())
    }

    override fun deleteFolder(folderId: String): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.deleteFolder(folderId) },
            onSuccess = { response ->
                response
            }
        )

    override fun getProjectFolders(projectId: String): Flow<Resource<List<Folder>>> = flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.fetchFolders(projectId) },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    override fun syncFoldersToLocal(projectId: String): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getGalleryFolders(projectId) },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSource.saveFolders(it) }
        )
        )
    }

    override fun syncImagesToLocal(projectId: String): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getGalleryImages(projectId) },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSource.saveImages(it) }
        )
        )
    }
}