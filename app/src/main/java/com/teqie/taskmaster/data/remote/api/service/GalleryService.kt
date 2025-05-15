package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.gallery.AddFolderRequestDto
import com.teqie.taskmaster.data.remote.dto.gallery.AddFolderResponseDto
import com.teqie.taskmaster.data.remote.dto.gallery.FoldersResponseDto
import com.teqie.taskmaster.data.remote.dto.gallery.ImageResponseDto
import com.teqie.taskmaster.data.remote.dto.gallery.SaveImageRequestDto
import com.teqie.taskmaster.ui.model.ResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GalleryService {
    @GET("folders/projects/{id}")
    suspend fun getGalleryFolders(@Path("id") projectId: String): Response<List<FoldersResponseDto>>

    @GET("folders/{id}/images")
    suspend fun getImagesByFolderId(@Path("id") projectId: String): Response<ImageResponseDto>

    @POST("images")
    suspend fun saveImage(@Body saveImageRequestDto: SaveImageRequestDto)

    @DELETE("images/{id}")
    suspend fun deleteImage(@Path("id") imageId: String): Response<ResponseMessage>

    @DELETE("folders/{id}")
    suspend fun deleteFolder(@Path("id") folderId: String): Response<ResponseMessage>

    @POST("folders")
    suspend fun addFolder(@Body addFolderRequestDto: AddFolderRequestDto): Response<AddFolderResponseDto>
}