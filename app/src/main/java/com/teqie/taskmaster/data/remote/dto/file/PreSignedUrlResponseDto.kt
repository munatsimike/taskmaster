package com.teqie.taskmaster.data.remote.dto.file

import kotlinx.serialization.Serializable

@Serializable
data class PreSignedUrlResponseDto(
    val url: String
)