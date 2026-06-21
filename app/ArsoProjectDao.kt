package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArsoProjectDao {
    @Query("SELECT * FROM arso_projects ORDER BY timestamp DESC")
    fun getAllProjects(): Flow<List<ArsoProject>>

    @Query("SELECT * FROM arso_projects WHERE id = :id")
    suspend fun getProjectById(id: Int): ArsoProject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ArsoProject): Long

    @Update
    suspend fun updateProject(project: ArsoProject)

    @Delete
    suspend fun deleteProject(project: ArsoProject)

    @Query("DELETE FROM arso_projects")
    suspend fun deleteAll()
}
