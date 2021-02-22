package com.mqa.jobwishlist.di

import com.mqa.jobwishlist.core.domain.usecase.JobInteractor
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideTourismUseCase(jobInteractor: JobInteractor): JobUseCase

}
