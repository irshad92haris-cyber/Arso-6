package com.example.data

import kotlinx.coroutines.flow.Flow

class ArsoRepository(private val arsoProjectDao: ArsoProjectDao) {
    val allProjects: Flow<List<ArsoProject>> = arsoProjectDao.getAllProjects()

    suspend fun getProjectById(id: Int): ArsoProject? {
        return arsoProjectDao.getProjectById(id)
    }

    suspend fun insert(project: ArsoProject): Long {
        return arsoProjectDao.insertProject(project)
    }

    suspend fun update(project: ArsoProject) {
        arsoProjectDao.updateProject(project)
    }

    suspend fun delete(project: ArsoProject) {
        arsoProjectDao.deleteProject(project)
    }
}
