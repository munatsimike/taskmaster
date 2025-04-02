package com.example.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggedInUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: LoggedInUserEntity)

    @Query("SELECT * FROM logged_in_user")
   fun getLoggedInUser(): Flow<LoggedInUserEntity?>

    @Query("DELETE FROM logged_in_user")
   suspend fun deleteLoggedInUser()
}