package com.mqa.jobwishlist.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

class SearchViewModel(jobUseCase: JobUseCase) : ViewModel() {

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchResult = queryChannel.asFlow()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            jobUseCase.getSearch(it)
        }
        .asLiveData(viewModelScope.coroutineContext)
}