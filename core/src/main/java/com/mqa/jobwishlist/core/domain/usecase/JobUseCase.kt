package com.mqa.jobwishlist.core.domain.usecase

import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.domain.model.Job
import kotlinx.coroutines.flow.Flow

interface JobUseCase {
    fun getAllJob(): Flow<Resource<List<Job>>>
    suspend fun getSearch(keywords: String): Resource<List<Job>>
    suspend fun getDescription(jobId: Int): Flow<Resource<Job>>
    fun getAllFullTimeJob(): Flow<Resource<List<Job>>>
    fun getAllPartTimeJob(): Flow<Resource<List<Job>>>
    fun getWishlistJob(): Flow<List<Job>>
    fun setWishlistJob(job: Job, state: Boolean)
    fun checkFavorite(id: Int): Flow<Int>
}