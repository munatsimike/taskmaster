package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "logged_in_user")
class LoggedInUserEntity (
    @PrimaryKey
    val id: String = "USER_FIXED_ID", // Fixed ID for single user
    val name: String,
    val email: String,
    val phone: String,
    val isSuperUser: Int,
    val avatar: String
)