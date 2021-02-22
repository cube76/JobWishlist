package com.mqa.jobwishlist.parttime

import android.content.Context
import com.mqa.jobwishlist.di.FullTimeModuleDependencies
import com.mqa.jobwishlist.di.PartTimeModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [PartTimeModuleDependencies::class])
interface PartTimeComponent {

    fun inject(activity: PartTimeActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: PartTimeModuleDependencies): Builder
        fun build(): PartTimeComponent
    }

}