package com.example.taskmaster.ui.model

data class UiMessage(
    var success: String? = null,
    val error: String? = null,
    val messageType: MessageType = MessageType.NONE,
    val id: Int = generateId()
) {
    companion object {
        private var currentId = 0
        private fun generateId(): Int {
            return ++currentId
        }
    }
}