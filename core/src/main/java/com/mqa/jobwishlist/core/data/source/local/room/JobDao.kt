package com.mqa.jobwishlist.core.data.source.local.room

import androidx.room.*
import com.mqa.jobwishlist.core.data.source.local.entity.JobEntity
import com.mqa.jobwishlist.core.data.source.local.entity.JobPartTimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("SELECT * FROM job")
    fun getAllJob(): Flow<List<JobEntity>>

    @Query("SELECT * FROM job")
    fun getSearch(): Flow<List<JobEntity>>

    @Query("SELECT * FROM job")
    fun getAllFullTimeJob(): Flow<List<JobEntity>>

    @Query("SELECT * FROM job")
    fun getAllPartTimeJob(): Flow<List<JobPartTimeEntity>>

    @Query("SELECT * FROM job")
    fun getDescription(): Flow<JobEntity>

    @Query("SELECT * FROM job where isFavorite = 1")
    fun getWishlistJob(): Flow<List<JobEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM job WHERE jobId=:id)")
    fun checkFavorite(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(tourism: List<JobEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobPartTime(tourism: List<JobPartTimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesc(job: JobEntity)

    @Update
    fun updateWishlistJob(tourism: JobEntity)
}
