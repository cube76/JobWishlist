package com.mqa.jobwishlist.ui.wishlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase

class WishlistViewModel @ViewModelInject constructor(jobUseCase: JobUseCase) : ViewModel() {
    val wishlistJob = jobUseCase.getWishlistJob().asLiveData()
}

