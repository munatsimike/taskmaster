package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.domain.model.RemoteResponse
import com.teqie.taskmaster.ui.model.ResponseMessage

object APIResponseMapper {
    fun RemoteResponse.toApiResponseMessage(): ResponseMessage {
        return ResponseMessage(
            message = message
        )
    }
}