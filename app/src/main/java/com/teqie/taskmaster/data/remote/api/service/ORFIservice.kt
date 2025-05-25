package com.teqie.taskmaster.data.remote.api.service


import com.teqie.taskmaster.data.remote.dto.orfi.CreateUpdateORFIRequest
import com.teqie.taskmaster.data.remote.dto.file.AddFileRequestDto
import com.teqie.taskmaster.data.remote.dto.file.CreateEditOrfiFileResponse
import com.teqie.taskmaster.data.remote.dto.orfi.ORFIFilesResponseDto
import com.teqie.taskmaster.data.remote.dto.orfi.OrfiDto
import com.teqie.taskmaster.ui.model.ResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ORFIservice {

    @GET("/orfi/project/{id}")
    suspend fun getORFI(@Path("id") projectId: String): Response<List<OrfiDto>>

    @GET("api/orfiFiles/orfi/{id}")
    suspend fun getORFIFiles(@Path("id") orfiId: String): Response<List<ORFIFilesResponseDto>>

    @POST("orfi")
    suspend fun createORFI(@Body createUpdateORFIRequest: CreateUpdateORFIRequest): Response<OrfiDto>

    @DELETE("orfi/{id}")
    suspend fun deleteORFI(@Path("id") orfiId: String): Response<ResponseMessage>

    @DELETE("api/orfiFiles/{id}")
    suspend fun deleteORFIFile(@Path("id") orfiFileId: String): Response<ResponseMessage>

    @PUT("orfi/{id}")
    suspend fun updateORFI(
        @Path("id") orfiId: String,
        @Body orfi: CreateUpdateORFIRequest
    ): Response<OrfiDto>

    @PUT("api/orfiFiles/{id}")
    suspend fun updateORFIFile(
        @Path("id") orfiFileId: String,
        @Body orfiFile: AddFileRequestDto
    ): Response<CreateEditOrfiFileResponse>

    @POST("api/orfiFiles")
    suspend fun createORFIFile(@Body orfiFile: AddFileRequestDto): Response<CreateEditOrfiFileResponse>
}