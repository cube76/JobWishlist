package com.mqa.jobwishlist.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.ui.JobAdapter
import com.mqa.jobwishlist.di.SearchModuleDependencies
import com.mqa.jobwishlist.search.databinding.ActivitySearchBinding
import com.mqa.jobwishlist.ui.desc.DescriptionActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val searchViewModel: SearchViewModel by viewModels {
        factory
    }

    private val jobAdapter = JobAdapter()
    private lateinit var binding: ActivitySearchBinding

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSearchComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    SearchModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Search Job"

        jobAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra(DescriptionActivity.EXTRA_DATA, selectedData.jobId)
            Log.v("search", selectedData.jobId.toString())
            startActivity(intent)
        }

        binding.svSearch.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(search: String?): Boolean {
                    lifecycleScope.launch {
                        search?.let { searchViewModel.queryChannel.send(it) }
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    return true
                }

                override fun onQueryTextChange(search: String?): Boolean {
                    lifecycleScope.launch {
                        search?.let { searchViewModel.queryChannel.send(it) }
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    return true
                }

            })

            setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(view, 0)
                }
            }
        }
        binding.svSearch.isIconified = true
        getJobData()

        with(binding.rvJob) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = jobAdapter
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun getJobData() {
        searchViewModel.searchResult.observe(this, { job ->
            if (job != null) {
                when (job) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (job.data != null) {
                            jobAdapter.setData(job.data)
                            binding.viewError.root.visibility = View.GONE
                            binding.viewEmpty.root.visibility = View.GONE
                        } else{
                            binding.viewEmpty.root.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}