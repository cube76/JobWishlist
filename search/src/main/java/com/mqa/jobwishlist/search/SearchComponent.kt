package com.mqa.jobwishlist.search

import android.content.Context
import com.mqa.jobwishlist.di.PartTimeModuleDependencies
import com.mqa.jobwishlist.di.SearchModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [SearchModuleDependencies::class])
interface SearchComponent {

    fun inject(activity: SearchActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: SearchModuleDependencies): Builder
        fun build(): SearchComponent
    }

}