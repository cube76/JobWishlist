package com.mqa.jobwishlist.fulltime

import android.content.Context
import com.mqa.jobwishlist.di.FullTimeModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FullTimeModuleDependencies::class])
interface FullTimeComponent {

    fun inject(activity: FullTimeActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: FullTimeModuleDependencies): Builder
        fun build(): FullTimeComponent
    }

}