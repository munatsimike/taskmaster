package com.example.taskmaster.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.domain.model.Schedule
import com.example.taskmaster.ui.common.CustomCard
import com.example.taskmaster.ui.model.ScheduleCategory
import com.example.taskmaster.ui.model.Status
import com.example.taskmaster.ui.theme.scheduleColor
import com.example.taskmaster.util.calculateStatus
import com.example.taskmaster.util.toColor


@Composable
fun ScheduleCard(
    scheduleList: List<Schedule>,
    cardBorderColor: Color = scheduleColor,
    onCardClick: () -> Unit
) {
    CustomCard(
        onImageClick = { onCardClick() },
        modifier = Modifier.fillMaxWidth(0.98f)
    ) {
        TaskSummaryTable(
            schedulesToScheduleCategory(scheduleList),
            scheduleList.size
        )
    }
}

@Composable
fun TaskSummaryTable(data: List<ScheduleCategory>, totalTasks: Int) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DisplayCardTitle(stringResource(id = R.string.schedule_overview))
        }
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.width(40.dp) // Reserve space for colored squares
            )
            Text(
                text = stringResource(id = R.string.tasks),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = stringResource(id = R.string.total, "($totalTasks)"),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
        HorizontalDivider()
        InsertTableRows(data)
    }
}

@Composable
private fun InsertTableRows(data: List<ScheduleCategory>) {
    // Data rows
    data.forEachIndexed { index, category ->
        val color =
            if (index % 2 == 0) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Unspecified
        Row(
            modifier = Modifier
                .background(color)
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Colored square
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(category.color)
            )

            // Category name
            Text(
                text = category.name.status,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp)
            )

            // Task count
            Text(
                text = category.count.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 15.dp)
            )
        }
        HorizontalDivider()
    }
}

private fun schedulesToScheduleCategory(schedules: List<Schedule>): List<ScheduleCategory> {
    // Initialize all statuses with a count of 0
    val allStatuses = Status.entries.associateWith { status ->
        ScheduleCategory(
            name = status,
            color = status.toColor(),
            count = 0 // Default count is 0
        )
    }.toMutableMap()

    // Group schedules by their status
    val groupedStatus = schedules.groupBy { schedule ->
        calculateStatus(schedule.completionAsaPercentage)
    }

    // Update the counts for existing statuses
    groupedStatus.forEach { (status, groupedSchedules) ->
        allStatuses[status] =
            allStatuses[status]?.copy(count = groupedSchedules.size) ?: ScheduleCategory(
                name = status,
                color = Color.Gray,
                count = groupedSchedules.size
            )
    }

    return allStatuses.values.toList()
}

