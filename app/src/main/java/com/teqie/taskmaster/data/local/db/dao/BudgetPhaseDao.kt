package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.BudgetPhaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetPhaseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBudgetPhase(budgetPhase: List<BudgetPhaseEntity>)

    @Query("SELECT * FROM budget_phase WHERE projectId=:projectId")
    fun fetchBudgetPhase(projectId: String): Flow<List<BudgetPhaseEntity>>

    @Query("DELETE FROM budget_phase")
    suspend fun deleteBudgetPhases()
}