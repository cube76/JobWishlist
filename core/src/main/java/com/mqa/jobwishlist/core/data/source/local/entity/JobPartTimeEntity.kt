package com.mqa.jobwishlist.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobPartTime")
data class JobPartTimeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "jobId")
    var id: Int,

    @ColumnInfo(name = "jobTitle")
    var title: String,

    @ColumnInfo(name = "employerName")
    var company: String,

    @ColumnInfo(name = "locationName")
    var location: String,

    @ColumnInfo(name = "jobDescription")
    var description: String,

    @ColumnInfo(name = "fullTime")
    var fullTime: Boolean,

    @ColumnInfo(name = "partTime")
    var partTime: Boolean,

    @ColumnInfo(name = "jobUrl")
    var url: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
