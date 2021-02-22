package com.mqa.jobwishlist.core.data.source.local

import com.mqa.jobwishlist.core.data.source.local.entity.JobEntity
import com.mqa.jobwishlist.core.data.source.local.entity.JobPartTimeEntity
import com.mqa.jobwishlist.core.data.source.local.room.JobDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val jobDao: JobDao) {

    fun getAllJob(): Flow<List<JobEntity>> = jobDao.getAllJob()

    fun getSearch(): Flow<List<JobEntity>> = jobDao.getSearch()

    fun getAllFullTimeJob(): Flow<List<JobEntity>> = jobDao.getAllFullTimeJob()

    fun getDescription(): Flow<JobEntity> = jobDao.getDescription()

    fun getAllPartTimeJob(): Flow<List<JobPartTimeEntity>> = jobDao.getAllPartTimeJob()

    fun getWishlistJob(): Flow<List<JobEntity>> = jobDao.getWishlistJob()

    fun checkFavorite(id: Int): Flow<Int> = jobDao.checkFavorite(id)

    suspend fun insertJob(jobList: List<JobEntity>) = jobDao.insertJob(jobList)

    suspend fun insertJobPartTime(jobList: List<JobPartTimeEntity>) = jobDao.insertJobPartTime(jobList)

    suspend fun insertDesc(jobList: JobEntity) = jobDao.insertDesc(jobList)

    fun setWishlistJob(job: JobEntity, newState: Boolean) {
        job.isFavorite = newState
        jobDao.updateWishlistJob(job)
    }
}