package com.mqa.jobwishlist.parttime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.ui.JobAdapter
import com.mqa.jobwishlist.di.PartTimeModuleDependencies
import com.mqa.jobwishlist.parttime.databinding.ActivityPartTimeBinding
import com.mqa.jobwishlist.ui.desc.DescriptionActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class PartTimeActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val mapsViewModel: PartTimeViewModel by viewModels {
        factory
    }

    private val jobAdapter = JobAdapter()
    private lateinit var binding: ActivityPartTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerPartTimeComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    PartTimeModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPartTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Part Time Job"

        jobAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra(DescriptionActivity.EXTRA_DATA, selectedData.jobId)
            Log.v("part time", selectedData.jobId.toString())
            startActivity(intent)
        }
        getJobData()

        with(binding.rvJobPartTime) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = jobAdapter
        }
    }

    private fun getJobData() {
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
                        binding.viewErrorPartTime.root.visibility = View.VISIBLE
                        binding.viewErrorPartTime.ivSomethingWrong
                    }
                }
            }
        })
    }
}