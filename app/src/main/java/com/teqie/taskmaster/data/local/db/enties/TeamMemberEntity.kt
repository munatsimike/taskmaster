package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_member")
class TeamMemberEntity (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val assignedAt: String?,
    val avatarUrl: String?,
    val email: String?,
    val isSuperUser: Int,
    val name: String,
    val phoneNumber: String?,
    val role: String,
    val username: String?
)