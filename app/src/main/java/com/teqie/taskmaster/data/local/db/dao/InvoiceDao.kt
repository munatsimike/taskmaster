package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.InvoiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInvoices(invoices: List<InvoiceEntity>)

    @Query("SELECT * FROM invoice WHERE budgetId=:budgetId")
    fun fetchInvoices(budgetId: String): Flow<List<InvoiceEntity>>

    @Query("DELETE FROM invoice")
    suspend fun deleteInvoices()
}