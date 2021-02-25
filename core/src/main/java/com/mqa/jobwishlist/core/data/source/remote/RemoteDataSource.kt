package com.mqa.jobwishlist.core.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.mqa.jobwishlist.core.data.source.remote.network.ApiResponse
import com.mqa.jobwishlist.core.data.source.remote.network.ApiService
import com.mqa.jobwishlist.core.data.source.remote.response.JobResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllJob(): Flow<ApiResponse<List<JobResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearch(keywords: String): Flow<ApiResponse<List<JobResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getListSearch(keywords)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllFullTimeJob(): Flow<ApiResponse<List<JobResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getListFullTime()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllPartTimeJob(): Flow<ApiResponse<List<JobResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getListPartTime()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                    Log.v("response", response.results.toString())
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDescription(jobId: Int): Flow<ApiResponse<JobResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getDescription(jobId)
                Log.v("response",response.description)
                emit(ApiResponse.Success(response))
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
                Log.e(TAG, "getDetail: ${ex.message} ")
            }
        }.flowOn(Dispatchers.IO)
    }
}

