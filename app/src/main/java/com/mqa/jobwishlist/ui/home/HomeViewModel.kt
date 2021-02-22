package com.mqa.jobwishlist.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase

class HomeViewModel @ViewModelInject constructor(jobUseCase: JobUseCase) : ViewModel() {
    val job = jobUseCase.getAllJob().asLiveData()
}

