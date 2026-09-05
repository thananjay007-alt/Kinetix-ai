package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {
    @Query("SELECT * FROM biomechanical_analyses ORDER BY timestamp DESC")
    fun getAllAnalyses(): Flow<List<AnalysisEntity>>

    @Query("SELECT * FROM biomechanical_analyses WHERE id = :id")
    suspend fun getAnalysisById(id: Long): AnalysisEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(entity: AnalysisEntity): Long

    @Update
    suspend fun updateAnalysis(entity: AnalysisEntity)

    @Delete
    suspend fun deleteAnalysis(entity: AnalysisEntity)

    @Query("UPDATE biomechanical_analyses SET isFavorite = :isFav WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFav: Boolean)

    @Query("DELETE FROM biomechanical_analyses WHERE id = :id")
    suspend fun deleteById(id: Long)
}
