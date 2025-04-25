package com.teqie.taskmaster.data.local.db.converters

import androidx.room.TypeConverter
import com.teqie.taskmaster.data.remote.dto.dashboard.BudgetPhaseDashBoardDto
import com.teqie.taskmaster.data.remote.dto.dashboard.TotalsResponseDto
import com.teqie.taskmaster.data.remote.dto.orfi.OrfiResponseDto
import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleResponseDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DashboardConverters {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {  ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = true}

    @TypeConverter
    fun fromBudgetPhaseList(list: List<BudgetPhaseDashBoardDto?>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toBudgetPhaseList(data: String): List<BudgetPhaseDashBoardDto?> {
        return json.decodeFromString(data)
    }

    // You’ll also need converters for OrfiResponseDto, ScheduleResponseDto, TotalsResponseDto
    @TypeConverter
    fun fromOrfiList(list: List<OrfiResponseDto>): String = json.encodeToString(list)

    @TypeConverter
    fun toOrfiList(data: String): List<OrfiResponseDto> = json.decodeFromString(data)

    @TypeConverter
    fun fromScheduleList(list: List<ScheduleResponseDto>): String = json.encodeToString(list)

    @TypeConverter
    fun toScheduleList(data: String): List<ScheduleResponseDto> = json.decodeFromString(data)

    @TypeConverter
    fun fromTotals(totals: TotalsResponseDto): String = json.encodeToString(totals)

    @TypeConverter
    fun toTotals(data: String): TotalsResponseDto = json.decodeFromString(data)
}
