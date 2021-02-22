package com.mqa.jobwishlist.core.utils

import com.mqa.jobwishlist.core.data.source.local.entity.JobEntity
import com.mqa.jobwishlist.core.data.source.local.entity.JobPartTimeEntity
import com.mqa.jobwishlist.core.data.source.remote.response.JobResponse
import com.mqa.jobwishlist.core.domain.model.Job

object DataMapper {
    fun mapResponsesToEntities(input: List<JobResponse>): List<JobEntity> {
        val jobList = ArrayList<JobEntity>()
        input.map {
            val job = JobEntity(
                id = it.id,
                title = it.title,
                company = it.company,
                location = it.location,
                description = it.description,
                fullTime = it.fullTime,
                partTime = it.partTime,
                url = it.url,
                isFavorite = false
            )
            jobList.add(job)
        }
        return jobList
    }

    fun mapResponsesToEntitiesPartTime(input: List<JobResponse>): List<JobPartTimeEntity> {
        val jobList = ArrayList<JobPartTimeEntity>()
        input.map {
            val job = JobPartTimeEntity(
                id = it.id,
                title = it.title,
                company = it.company,
                location = it.location,
                description = it.description,
                fullTime = it.fullTime,
                partTime = it.partTime,
                url = it.url,
                isFavorite = false
            )
            jobList.add(job)
        }
        return jobList
    }

    fun mapEntitiesToDomain(input: List<JobEntity>): List<Job> =
        input.map {
            Job(
                jobId = it.id,
                jobTitle = it.title,
                employerName = it.company,
                locationName = it.location,
                jobDescription = it.description,
                fullTime = it.fullTime,
                    partTime = it.partTime,
                url = it.url,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesPartTimeToDomain(input: List<JobPartTimeEntity>): List<Job> =
        input.map {
            Job(
                jobId = it.id,
                jobTitle = it.title,
                employerName = it.company,
                locationName = it.location,
                jobDescription = it.description,
                fullTime = it.fullTime,
                partTime = it.partTime,
                url = it.url,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Job) = JobEntity(
        id = input.jobId,
        title = input.jobTitle,
        company = input.employerName,
        location = input.locationName,
        description = input.jobDescription,
            fullTime = input.fullTime,
            partTime = input.partTime,
        url = input.url,
        isFavorite = input.isFavorite
    )

    fun mapDetailResponseToDomain(input: JobResponse): Job {
        return Job(
                jobId = input.id,
                jobTitle = input.title,
                employerName = input.company,
                locationName = input.location,
                jobDescription = input.description,
                fullTime = input.fullTime,
                partTime = input.partTime,
                url = input.url,
                isFavorite = input.isFavorite
        )
    }

    fun mapResponseToDomain(input: List<JobResponse>): List<Job> {
        return input.map {
            Job(
                jobId = it.id,
                jobTitle = it.title,
                employerName = it.company,
                locationName = it.location,
                jobDescription = it.description,
                fullTime = it.fullTime,
                partTime = it.partTime,
                url = it.url,
                isFavorite = it.isFavorite
            )
        }
    }
}