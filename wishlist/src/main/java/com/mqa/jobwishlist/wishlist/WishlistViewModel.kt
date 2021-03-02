package com.mqa.jobwishlist.wishlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mqa.jobwishlist.core.domain.usecase.JobUseCase
import javax.inject.Inject

class WishlistViewModel (jobUseCase: JobUseCase) : ViewModel() {
    val wishlistJob = jobUseCase.getWishlistJob().asLiveData()
}

