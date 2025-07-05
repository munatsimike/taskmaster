package com.teqie.taskmaster.data.repository

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.FileToAddFileRequestDtoMapper.toDtoModel
import com.teqie.taskmaster.data.mapper.FileToSaveImageDto
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.file.PresignedUrl
import com.teqie.taskmaster.domain.util.FileExtension
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FileManagementRepoImpl(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource
): FileManagementRepository, BaseRepository() {

    override fun getPreSignedUrl(fileName: String, fileType: String): Flow<Resource<PresignedUrl>> =
        processApiResponse(
            call = { remoteDataSource.getPreSignedUrl(fileName, fileType) },
            onSuccess = { response ->
                PresignedUrl(response.url)
            }
        )

    override fun uploadFileToPreSignedUrl(file: File, preSignedUrl: PresignedUrl): String {
        return remoteDataSource.uploadFileToPreSignedUrl(file, preSignedUrl)
    }

    override fun deleteFile(fileId: String, fileType: FileType): Flow<Resource<ResponseMessage>> = flow {
        try {
            when (fileType) {
                FileType.InvoiceFile -> {
                    remoteDataSource.deleteInvoiceFile(fileId)
                }

                FileType.ORFIFile -> {
                    remoteDataSource.deleteORFIFile(fileId)
                }

                FileType.IMAGE -> {
                    remoteDataSource.deleteImage(fileId)
                }

                FileType.UNKNOWN -> {
                    throw IllegalArgumentException("Unknown file type")
                }
            }

            emit(Resource.Success(ResponseMessage("file deleted successfully")))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
    override fun editFile(fileData: FileData): Flow<Resource<ResponseMessage>> = flow {
        emit(Resource.Success(data = ResponseMessage()))
    }

    override suspend fun saveFile(fileData: FileData) {
        when (fileData.fileType) {
            FileType.InvoiceFile -> {
                remoteDataSource.addInvoiceFile(fileData.toDtoModel())
            }

            FileType.ORFIFile -> {
                remoteDataSource.saveORFIFile(fileData.toDtoModel())
            }

            FileType.IMAGE -> {
               remoteDataSource.saveImageFile(FileToSaveImageDto.toDtoModel(fileData))
            }

            FileType.UNKNOWN -> {}
        }
    }

    // Downloads and saves the file
    override suspend fun downloadFile(
        fileUrl: String,
        fileName: String,
        fileType: FileExtension,
        progress: (Int) -> Unit
    ) {
        // Perform network request on IO dispatcher
        val response = remoteDataSource.downloadFile(fileUrl)
        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                localDataSource.saveFileToStorage(responseBody, fileName, fileType, progress)
            } ?: throw Exception("response is empty")
        }
    }
}