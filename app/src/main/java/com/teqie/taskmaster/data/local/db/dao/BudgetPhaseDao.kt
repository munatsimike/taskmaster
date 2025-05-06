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

    @Query("SELECT * FROM budget_phase WHERE id=:id")
    fun fetchBudgetPhase(id: String): Flow<List<BudgetPhaseEntity>>
}