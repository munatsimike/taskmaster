package com.teqie.taskmaster.ui.screen.schedule

data class ScheduleFormState(
    val id: String = "",
    var startDate: String = "",
    val totalDuration: String = "",
    val isDeleted: Boolean = false,
    val progress: String = "",
    val phase: String =""
)