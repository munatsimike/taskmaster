package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import com.teqie.taskmaster.data.local.db.enties.OrfiFileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrfiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrfi(orfi: List<OrfiEntity>)

    @Query("SELECT * FROM orfi WHERE projectId=:projectId")
    fun fetchOrfi(projectId: String): Flow<List<OrfiEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrfiFile(orfi: List<OrfiFileEntity>)

    @Query("SELECT * FROM orfi_files WHERE id=:projectId")
    fun fetchOrfiFile(projectId: String): Flow<List<OrfiFileEntity>>

    @Query("DELETE FROM orfi")
    suspend fun deleteOrfis()
}