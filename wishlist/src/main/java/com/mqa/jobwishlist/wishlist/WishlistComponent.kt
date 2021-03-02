package com.mqa.jobwishlist.wishlist

import android.content.Context
import com.mqa.jobwishlist.di.SearchModuleDependencies
import com.mqa.jobwishlist.di.WishlistModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [WishlistModuleDependencies::class])
interface WishlistComponent {

    fun inject(fragment: WishlistFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: WishlistModuleDependencies): Builder
        fun build(): WishlistComponent
    }

}