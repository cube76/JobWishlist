package com.mqa.jobwishlist.core.data.source.remote.network

import com.mqa.jobwishlist.core.data.source.remote.response.JobResponse
import com.mqa.jobwishlist.core.data.source.remote.response.ListJobResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getList(): ListJobResponse

    @GET("search?")
    suspend fun getListSearch(@Query("keywords") keywords: String): ListJobResponse

    @GET("search?fullTime=true&partTime=false")
    suspend fun getListFullTime(): ListJobResponse

    @GET("search?partTime=true&fullTime=false")
    suspend fun getListPartTime(): ListJobResponse

    @GET("jobs/{job_id}")
    suspend fun getDescription(@Path("job_id") jobId: Int): JobResponse
}
