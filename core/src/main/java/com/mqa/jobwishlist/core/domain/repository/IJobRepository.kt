package com.mqa.jobwishlist.core.domain.repository

import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.domain.model.Job
import kotlinx.coroutines.flow.Flow

interface IJobRepository {

    fun getAllJob(): Flow<Resource<List<Job>>>

    suspend fun getSearch(keywords: String): Resource<List<Job>>

    fun getAllFullTimeJob(): Flow<Resource<List<Job>>>

    fun getAllPartTimeJob(): Flow<Resource<List<Job>>>

    suspend fun getDescription(jobId: Int): Flow<Resource<Job>>

    fun getWishlistJob(): Flow<List<Job>>

    fun setWishlistJob(job: Job, state: Boolean)

    fun checkFavorite(id: Int): Flow<Int>

}