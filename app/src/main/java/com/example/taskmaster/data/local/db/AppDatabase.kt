package com.example.taskmaster.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmaster.data.local.db.converters.DashboardConverters
import com.example.taskmaster.data.local.db.dao.DashboardDao
import com.example.taskmaster.data.local.db.dao.LoggedInUserDao
import com.example.taskmaster.data.local.db.dao.ProjectDao
import com.example.taskmaster.data.local.db.enties.DashboardEntity
import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.example.taskmaster.data.local.db.enties.ProjectEntity

@Database(entities = [LoggedInUserEntity::class, ProjectEntity::class, DashboardEntity::class], version = 2)
@TypeConverters(DashboardConverters::class)
abstract class AppDatabase : RoomDatabase(){
  abstract  val loggedInDao: LoggedInUserDao
  abstract val projectDao: ProjectDao
  abstract val dashboardDao: DashboardDao
}