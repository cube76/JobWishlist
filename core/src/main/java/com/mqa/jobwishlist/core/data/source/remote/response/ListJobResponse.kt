package com.mqa.jobwishlist.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListJobResponse (
    @field:SerializedName("results")
    val results: List<JobResponse>
)