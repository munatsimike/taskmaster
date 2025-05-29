package com.teqie.taskmaster.data.remote.dto.orfi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ORFIFilesResponseDto(
    val description: String,
    val fileName: String,
    val id: String,
    val isDeleted: Int,
    @SerialName("orfi_id")
    val orfiId: String,
    val url: String
)