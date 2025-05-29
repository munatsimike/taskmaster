package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.FolderEntity
import com.teqie.taskmaster.data.local.db.enties.GalleryImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(images: List<GalleryImageEntity>)

    @Query("SELECT * FROM images WHERE id=:folderId")
    fun fetchImages(folderId: String): Flow<List<GalleryImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFolders(images: List<FolderEntity>)

    @Query("SELECT * FROM folders WHERE projectId=:projectId")
    fun fetchFolders(projectId: String): Flow<List<FolderEntity>>
}