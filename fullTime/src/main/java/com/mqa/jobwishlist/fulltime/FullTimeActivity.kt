package com.mqa.jobwishlist.fulltime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.ui.JobAdapter
import com.mqa.jobwishlist.di.FullTimeModuleDependencies
import com.mqa.jobwishlist.fulltime.databinding.ActivityFullTimeBinding
import com.mqa.jobwishlist.ui.desc.DescriptionActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FullTimeActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val mapsViewModel: FullTimeViewModel by viewModels {
        factory
    }

    private val jobAdapter = JobAdapter()
    private lateinit var binding: ActivityFullTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFullTimeComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FullTimeModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFullTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Full Time Job"

        jobAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra(DescriptionActivity.EXTRA_DATA, selectedData.jobId)
            startActivity(intent)
        }
        getTourismData()

        with(binding.rvJob) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = jobAdapter
        }
    }

    private fun getTourismData() {
        mapsViewModel.job.observe(this, { job ->
            if (job != null) {
                when (job) {
                    is Resource.Loading -> binding.progressBar2.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        jobAdapter.setData(job.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        binding.viewError2.root.visibility = View.VISIBLE
                        binding.viewError2.ivSomethingWrong
                    }
                }
            }
        })
    }
}