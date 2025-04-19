package com.example.taskmaster.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmaster.data.local.db.dao.LoggedInUserDao
import com.example.taskmaster.data.local.db.dao.ProjectDao
import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.example.taskmaster.data.local.db.enties.ProjectEntity

@Database(entities = [LoggedInUserEntity::class, ProjectEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
  abstract  val loggedInDao: LoggedInUserDao
  abstract val projectDao: ProjectDao
}