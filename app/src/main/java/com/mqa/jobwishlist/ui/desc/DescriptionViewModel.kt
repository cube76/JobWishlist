package com.mqa.jobwishlist.ui.desc

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.domain.model.Job
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DescriptionViewModel @ViewModelInject constructor(private val jobUseCase: JobUseCase) : ViewModel() {

    lateinit var detail: LiveData<Resource<Job>>

    fun setWishlistJob(job: Job, newStatus:Boolean) =
        jobUseCase.setWishlistJob(job, newStatus)

    fun getDescription(id: Int) = viewModelScope.launch {
        if (!::detail.isInitialized) {
            detail = jobUseCase.getDescription(id)
                    .onStart {
                        emit(Resource.Loading())
                        Log.d("VM", "getDetail: $id")
                    }
                    .catch { exception -> Resource.Error(exception.toString(), null) }
                    .asLiveData()
        }
    }

    fun checkFavorite(id: Int) = jobUseCase.checkFavorite(id).asLiveData()

}

