package com.mqa.jobwishlist.fulltime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val jobUseCase: JobUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FullTimeViewModel::class.java) -> {
                FullTimeViewModel(jobUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}