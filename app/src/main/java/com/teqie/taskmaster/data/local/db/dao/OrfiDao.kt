package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrfiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrfi(orfi: List<OrfiEntity>)

    @Query("SELECT * FROM orfi WHERE projectId=:projectId")
    fun fetchOrfi(projectId: String): Flow<List<OrfiEntity>>
}