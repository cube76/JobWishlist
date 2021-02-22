package com.mqa.jobwishlist.di

import com.mqa.jobwishlist.core.domain.usecase.JobUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface SearchModuleDependencies {

    fun jobUseCase(): JobUseCase
}