package com.example.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmaster.data.remote.dto.dashboard.BudgetPhaseDashBoardDto
import com.example.taskmaster.data.remote.dto.dashboard.TotalsResponseDto
import com.example.taskmaster.data.remote.dto.orfi.OrfiResponseDto
import com.example.taskmaster.data.remote.dto.schedule.ScheduleResponseDto

@Entity(tableName = "dashboard")
class DashboardEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val budgetPhases: List<BudgetPhaseDashBoardDto?>,
    val orfis: List<OrfiResponseDto>,
    val schedules: List<ScheduleResponseDto>,
    val totals: TotalsResponseDto
)