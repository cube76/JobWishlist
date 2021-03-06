package com.mqa.jobwishlist.core.di

import android.content.Context
import androidx.room.Room
import com.mqa.jobwishlist.core.data.source.local.room.JobDao
import com.mqa.jobwishlist.core.data.source.local.room.JobDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    val passphrase: ByteArray = SQLiteDatabase.getBytes("mqa".toCharArray())
    val factory = SupportFactory(passphrase)
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): JobDatabase = Room.databaseBuilder(
        context,
        JobDatabase::class.java, "Job.db"
    ).fallbackToDestructiveMigration().openHelperFactory(factory).build()

    @Provides
    fun provideTourismDao(database: JobDatabase): JobDao = database.jobDao()
}