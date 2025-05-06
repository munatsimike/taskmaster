package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTeamMembers(members: List<TeamMemberEntity>)

    @Query("SELECT * FROM team_member WHERE id=:projectId")
    fun getProjectTeamMembers(projectId: String): Flow<List<TeamMemberEntity>>
}