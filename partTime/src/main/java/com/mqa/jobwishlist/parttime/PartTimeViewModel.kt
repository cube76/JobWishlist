package com.mqa.jobwishlist.parttime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase

class PartTimeViewModel(jobUseCase: JobUseCase) : ViewModel() {
    val job = jobUseCase.getAllPartTimeJob().asLiveData()
}