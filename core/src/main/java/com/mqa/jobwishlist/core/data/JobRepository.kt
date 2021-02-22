package com.mqa.jobwishlist.core.data

import com.mqa.jobwishlist.core.data.source.remote.network.ApiResponse
import com.mqa.jobwishlist.core.data.source.local.LocalDataSource
import com.mqa.jobwishlist.core.data.source.remote.RemoteDataSource
import com.mqa.jobwishlist.core.data.source.remote.response.JobResponse
import com.mqa.jobwishlist.core.domain.model.Job
import com.mqa.jobwishlist.core.domain.repository.IJobRepository
import com.mqa.jobwishlist.core.utils.AppExecutors
import com.mqa.jobwishlist.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IJobRepository {

    override fun getAllJob(): Flow<Resource<List<Job>>> =
            object : NetworkBoundResource<List<Job>, List<JobResponse>>() {
                override fun loadFromDB(): Flow<List<Job>> {
                    return localDataSource.getSearch().map {
                        DataMapper.mapEntitiesToDomain(it)
                    }
                }

                override fun shouldFetch(data: List<Job>?): Boolean =
//                data == null || data.isEmpty()
                        true // ganti dengan true jika ingin selalu mengambil data dari internet

                override suspend fun createCall(): Flow<ApiResponse<List<JobResponse>>> =
                        remoteDataSource.getAllJob()

                override suspend fun saveCallResult(data: List<JobResponse>) {
                    val tourismList = DataMapper.mapResponsesToEntities(data)
                    localDataSource.insertJob(tourismList)
                }
            }.asFlow()

    override suspend fun getSearch(keywords: String): Resource<List<Job>> {
        return when (val apiResponse = remoteDataSource.getSearch(keywords).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapResponseToDomain(apiResponse.data)
                Resource.Success(result)
            }
            is ApiResponse.Empty -> {
                Resource.Error(apiResponse.toString())
            }
            is ApiResponse.Error -> {
                Resource.Error(apiResponse.errorMessage)
            }
        }
    }


    override fun getAllFullTimeJob(): Flow<Resource<List<Job>>> =
            object : NetworkBoundResource<List<Job>, List<JobResponse>>() {
                override fun loadFromDB(): Flow<List<Job>> {
                    return localDataSource.getAllFullTimeJob().map {
                        DataMapper.mapEntitiesToDomain(it)
                    }
                }

                override fun shouldFetch(data: List<Job>?): Boolean =
//                data == null || data.isEmpty()
                        true // ganti dengan true jika ingin selalu mengambil data dari internet

                override suspend fun createCall(): Flow<ApiResponse<List<JobResponse>>> =
                        remoteDataSource.getAllFullTimeJob()

                override suspend fun saveCallResult(data: List<JobResponse>) {
                    val tourismList = DataMapper.mapResponsesToEntities(data)
                    localDataSource.insertJob(tourismList)
                }
            }.asFlow()

    override fun getAllPartTimeJob(): Flow<Resource<List<Job>>> =
            object : NetworkBoundResource<List<Job>, List<JobResponse>>() {
                override fun loadFromDB(): Flow<List<Job>> {
                    return localDataSource.getAllPartTimeJob().map {
                        DataMapper.mapEntitiesPartTimeToDomain(it)
                    }
                }

                override fun shouldFetch(data: List<Job>?): Boolean =
//                data == null || data.isEmpty()
                        true // ganti dengan true jika ingin selalu mengambil data dari internet

                override suspend fun createCall(): Flow<ApiResponse<List<JobResponse>>> =
                        remoteDataSource.getAllPartTimeJob()

                override suspend fun saveCallResult(data: List<JobResponse>) {
                    val tourismList = DataMapper.mapResponsesToEntitiesPartTime(data)
                    localDataSource.insertJobPartTime(tourismList)
                }
            }.asFlow()

    override suspend fun getDescription(jobId: Int): Flow<Resource<Job>> {

            return remoteDataSource.getDescription(jobId).map {
                when (it) {
                    is ApiResponse.Success -> Resource.Success(DataMapper.mapDetailResponseToDomain(it.data))
                    is ApiResponse.Empty -> Resource.Error(it.toString())
                    is ApiResponse.Error -> Resource.Error(it.errorMessage)
                }
            }
    }

    override fun checkFavorite(id: Int): Flow<Int> {
        return localDataSource.checkFavorite(id)
    }

    override fun getWishlistJob(): Flow<List<Job>> {
        return localDataSource.getWishlistJob().map {
           DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setWishlistJob(job: Job, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(job)
        appExecutors.diskIO().execute { localDataSource.setWishlistJob(tourismEntity, state) }
    }
}

