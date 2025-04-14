package com.example.taskmaster.data.mapper

import com.example.taskmaster.domain.model.APIResponse
import com.example.taskmaster.ui.model.APIResponseMessage

object APIResponseMapper {
    fun APIResponse.toApiResponseMessage(): APIResponseMessage {
        return APIResponseMessage(
            message = message
        )
    }
}