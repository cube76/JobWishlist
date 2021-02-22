package com.mqa.jobwishlist.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class JobResponse(
    @field:SerializedName("jobId")
    val id: Int,

    @field:SerializedName("jobTitle")
    val title: String,

    @field:SerializedName("employerName")
    val company: String,

    @field:SerializedName("locationName")
    val location: String,

    @field:SerializedName("jobDescription")
    val description: String,

    @field:SerializedName("fullTime")
    val fullTime: Boolean,

    @field:SerializedName("partTime")
    val partTime: Boolean,

    @field:SerializedName("jobUrl")
    val url: String,

    @field:SerializedName("isFavorite")
    val isFavorite: Boolean

)

