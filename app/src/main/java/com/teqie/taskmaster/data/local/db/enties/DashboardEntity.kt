package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.teqie.taskmaster.data.remote.dto.dashboard.BudgetPhaseDashBoardDto
import com.teqie.taskmaster.data.remote.dto.dashboard.TotalsResponseDto
import com.teqie.taskmaster.data.remote.dto.orfi.OrfiResponseDto
import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleResponseDto

@Entity(tableName = "dashboard")
class DashboardEntity (
    @PrimaryKey
    val projectId: String,
    val budgetPhases: List<BudgetPhaseDashBoardDto?>,
    val orfis: List<OrfiResponseDto>,
    val schedules: List<ScheduleResponseDto>,
    val totals: TotalsResponseDto
)