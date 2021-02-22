package com.mqa.jobwishlist.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val jobId: Int,
    val jobTitle: String,
    val employerName: String,
    val locationName: String,
    val jobDescription: String,
    val fullTime: Boolean,
    val partTime: Boolean,
    val url: String,
    val isFavorite: Boolean
) : Parcelable