package com.teqie.taskmaster.ui.components.form

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.util.isoToReadableDate

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CustomDatePicker(
    startDate: String,
    modifier: Modifier = Modifier,
    labelTxt: String = "Start Date",
    setStartDate: (String) -> Unit
) {
    val datePicker = datePicker(startDate) { date ->
        setStartDate(date)
    }

    Box {
        OutlinedTextField(
            value = startDate.isoToReadableDate(),
            onValueChange = {},
            label = { Text(labelTxt) },
            modifier = Modifier
                .then(modifier)
                .fillMaxWidth(0.7f),
            colors = customTxtFieldColors(),
            readOnly = true,
            trailingIcon = {
                DisplayImageVectorIcon(
                    icon = ImageVector.vectorResource(id = R.drawable.calendar_month_24px),
                    iconSize = 35,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { datePicker.show() })
            }
        )
    }
}

@Composable
private fun datePicker(
    initialDAte: String,
    setStartDate: (String) -> Unit
): DatePickerDialog {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Parse the startDate from ISO format to initialize the calendar
    initialDAte.let { isoDate ->
        if (isoDate.isNotBlank()) {
            try {
                // Parse the ISO string to a ZonedDateTime
                val zonedDateTime =
                    ZonedDateTime.parse(isoDate, DateTimeFormatter.ISO_DATE_TIME)
                // Set calendar to the parsed date
                calendar.set(
                    zonedDateTime.year,
                    zonedDateTime.monthValue - 1, // Month is 0-based in Calendar
                    zonedDateTime.dayOfMonth
                )
            } catch (e: Exception) {
                e.printStackTrace() // Handle parsing errors
            }
        }
    }

    // Date picker dialog
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Create a ZonedDateTime instance from the selected date in UTC
            val selectedDate = ZonedDateTime.of(
                year,
                month + 1, // Month is 0-based in Calendar
                dayOfMonth,
                0, 0, 0, 0, // Set time to start of the day
                ZoneId.of("UTC")
            )

            // Convert ZonedDateTime to ISO-8601 string with `Z` (UTC timezone)
            val isoDateString = selectedDate.format(DateTimeFormatter.ISO_INSTANT)
            // Set selected date in the ViewModel as an ISO string
            setStartDate(isoDateString)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}