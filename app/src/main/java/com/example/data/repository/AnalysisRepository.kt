package com.example.data.repository

import com.example.data.local.AnalysisDao
import com.example.data.local.AnalysisEntity
import kotlinx.coroutines.flow.Flow

class AnalysisRepository(private val analysisDao: AnalysisDao) {
    val allAnalyses: Flow<List<AnalysisEntity>> = analysisDao.getAllAnalyses()

    suspend fun getById(id: Long): AnalysisEntity? = analysisDao.getAnalysisById(id)

    suspend fun insert(entity: AnalysisEntity): Long = analysisDao.insertAnalysis(entity)

    suspend fun delete(entity: AnalysisEntity) = analysisDao.deleteAnalysis(entity)

    suspend fun deleteById(id: Long) = analysisDao.deleteById(id)

    suspend fun toggleFavorite(id: Long, isFav: Boolean) = analysisDao.updateFavorite(id, isFav)
}
