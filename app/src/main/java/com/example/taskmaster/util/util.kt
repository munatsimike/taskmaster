package com.example.taskmaster.util

import androidx.compose.ui.graphics.Color
import com.example.taskmaster.domain.LoggedInUser
import com.example.taskmaster.ui.model.Status
import com.example.taskmaster.ui.common.header.HeaderData
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun headerData(
    loggedInUser: LoggedInUser,
    projectName: String,
    currentPage: String,
    showBackBtn: Boolean = false
): HeaderData =
    HeaderData(
        loggedInUser = loggedInUser,
        projectTitle = projectName,
        currentPage = currentPage,
        showBackButton = showBackBtn,
    )

fun formatFloat(value: Float): String {
    return if (value % 1 == 0f) {
        value.toInt().toString() // Convert to an integer if no decimal
    } else {
        value.toString() // Keep the float as is
    }
}

fun Status.toColor(): Color {
    return when (this) {
        Status.NOT_STARTED -> Color.Gray
        Status.COMPLETED -> Color(0xFF35886A)
        Status.OVER_DUE -> Color(0xFFEE5850)
        Status.IN_PROGRESS -> Color(0xFFEBD513)
    }
}

fun calculateStatus(completionPercentage: Int): Status {
    return when {
        completionPercentage == 0 -> Status.NOT_STARTED
        completionPercentage == 100 -> Status.COMPLETED
        completionPercentage > 100 -> Status.OVER_DUE
        else -> Status.IN_PROGRESS
    }
}

enum class DateFormat(val format: String) {
    SIMPLE("yyyy-MM-dd"),
    FULL("MMMM dd, yyyy");
}


fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(amount)
}

fun isodateToLocalDate(stringDate: String): LocalDate? {
    val instant = Instant.parse(stringDate)
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}


fun String.isoToReadableDate(
    dateFormat: DateFormat = DateFormat.FULL
): String {
    if (this.isNotEmpty()) {
        // Parse the ISO 8601 date
        val zonedDateTime = ZonedDateTime.parse(this)
        // Format it to a more readable form, e.g., "September 16, 2024, 10:00 PM"
        val formatter = DateTimeFormatter.ofPattern(dateFormat.format)
        return zonedDateTime.format(formatter)
    }
    return ""
}

fun LocalDate.toIsoString(): String {
    return this.atStartOfDay(ZoneId.of("UTC"))
        .format(DateTimeFormatter.ISO_INSTANT)
}

fun String.isoStringToLocalDate(): LocalDate {
    return ZonedDateTime.parse(this).toLocalDate() // Parse full date-time string and extract LocalDate
}