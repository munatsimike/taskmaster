package com.example.taskmaster.data.mapper

import com.example.taskmaster.domain.model.RemoteResponse
import com.example.taskmaster.ui.model.ResponseMessage

object APIResponseMapper {
    fun RemoteResponse.toApiResponseMessage(): ResponseMessage {
        return ResponseMessage(
            message = message
        )
    }
}