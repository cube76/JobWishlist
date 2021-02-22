package com.mqa.jobwishlist.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mqa.jobwishlist.core.data.source.local.entity.JobEntity
import com.mqa.jobwishlist.core.data.source.local.entity.JobPartTimeEntity
import com.mqa.jobwishlist.core.data.source.local.room.JobDao

@Database(entities = [JobEntity::class, JobPartTimeEntity::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {

    abstract fun jobDao(): JobDao

}