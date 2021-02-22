package com.mqa.jobwishlist.fulltime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase

class FullTimeViewModel(jobUseCase: JobUseCase) : ViewModel() {
    val job = jobUseCase.getAllFullTimeJob().asLiveData()
}