package com.mqa.jobwishlist.core.domain.usecase

import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.domain.model.Job
import com.mqa.jobwishlist.core.domain.repository.IJobRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JobInteractor @Inject constructor(private val jobRepository: IJobRepository): JobUseCase {

    override fun getAllJob() = jobRepository.getAllJob()

    override suspend fun getSearch(keywords: String) = jobRepository.getSearch(keywords)

    override fun getAllFullTimeJob() = jobRepository.getAllFullTimeJob()

    override fun getAllPartTimeJob() = jobRepository.getAllPartTimeJob()

    override suspend fun getDescription(jobId: Int) = jobRepository.getDescription(jobId)

    override fun checkFavorite(id: Int): Flow<Int> = jobRepository.checkFavorite(id)

    override fun getWishlistJob() = jobRepository.getWishlistJob()

    override fun setWishlistJob(job: Job, state: Boolean) = jobRepository.setWishlistJob(job, state)
}