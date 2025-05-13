package com.teqie.taskmaster.ui.model

import com.teqie.taskmaster.domain.model.file.PresignedUrl

sealed class FileState: ScreenState(){
    data object Idle: FileState()
    data class Uploaded(val uploadedUrl: String): FileState()
    data class PresignedUrlGenerated(val presignedUrl: PresignedUrl): FileState()
}