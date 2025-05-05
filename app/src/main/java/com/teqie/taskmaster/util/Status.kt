package com.teqie.taskmaster.util

enum class Status(val status: String) {
    COMPLETED("Completed"),
    IN_PROGRESS("In progress"),
    OVER_DUE("Overdue"),
    NOT_STARTED("Not Started")
}