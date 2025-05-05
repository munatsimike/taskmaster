package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.file.PreSignedUrlResponseDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface FileManagerService {

    @GET("file/generate-presigned-url")
    suspend fun getPreSignedUrl(
        @Query("fileName") fileName: String,
        @Query("fileType") fileType: String
    ): Response<PreSignedUrlResponseDto>

    @Streaming
    @GET
    suspend fun donwloadFile(@Url fileUrl: String): Response<ResponseBody>

}