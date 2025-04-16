package com.example.taskmaster.domain.model

import com.example.taskmaster.util.formatFloat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class Schedule(
    val id: String,
    val isDeleted: Boolean?,
    val phase: String,
    val progress: Float,
    val project_id: String,
    val startDate: String,
    val totalDuration: Int   //
) {
    val completion: Float get() = progress.div(totalDuration)

    val completionAsaPercentage: Int
        get() = (completion * 100).toInt()

    fun isDeadLIneExceed(): Boolean {
        return progress > totalDuration
    }

    fun calculateOverdueMonths(): String {
        return formatFloat(progress - totalDuration)
    }

    fun calculateRemainingMonths(): String {
        return if (totalDuration > progress) {
            formatFloat(totalDuration - progress)
        } else {
            "0"
        }
    }

     fun getCompletionDate(
        outputFormat: String = "MMMM dd, yyyy"
    ): String {
        return try {
            // Parse the ISO 8601 date string
            val zonedDateTime = ZonedDateTime.parse(startDate)

            // Add the specified number of months
            val updatedDate = zonedDateTime.plusMonths(totalDuration.toLong())

            // Format the updated date to the desired format
            val formatter = DateTimeFormatter.ofPattern(outputFormat)
            updatedDate.format(formatter)
        } catch (e: Exception) {
            // Handle invalid ISO date format
            "Invalid Date"
        }
    }
}
