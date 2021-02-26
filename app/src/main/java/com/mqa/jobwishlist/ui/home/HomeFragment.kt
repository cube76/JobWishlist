package com.mqa.jobwishlist.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.ui.JobAdapter
import com.mqa.jobwishlist.databinding.FragmentHomeBinding
import com.mqa.jobwishlist.ui.desc.DescriptionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData()
    }

    private fun showData(){

                if (activity != null) {

                    val jobAdapter = JobAdapter()
                    jobAdapter.onItemClick = { selectedData ->
                        val intent = Intent(activity, DescriptionActivity::class.java)
                        intent.putExtra(DescriptionActivity.EXTRA_DATA, selectedData.jobId)
                        Log.e("extra home", selectedData.jobId.toString())
                        startActivity(intent)
                    }

                    homeViewModel.job.observe(viewLifecycleOwner, { job ->
                        if (job != null) {
                            when (job) {
                                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    jobAdapter.setData(job.data)
                                }
                                is Resource.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    binding.viewError.root.visibility = View.VISIBLE
                                }
                            }
                        }
                    })

                    with(binding.rvTourism) {
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapter = jobAdapter
                    }
                }
            }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
