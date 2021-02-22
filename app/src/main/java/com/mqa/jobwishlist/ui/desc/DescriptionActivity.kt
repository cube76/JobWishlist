package com.mqa.jobwishlist.ui.desc

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mqa.jobwishlist.R
import com.mqa.jobwishlist.core.data.Resource
import com.mqa.jobwishlist.core.domain.model.Job
import com.mqa.jobwishlist.core.utils.ConvertHtml
import com.mqa.jobwishlist.databinding.ActivityDetailJobBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_DATA_WISHLIST = "extra_data_favorite"
    }

    private var jobId: Int = 0
    private var jobIdHome: Int = 0
    private var jobIdWishlist: Int = 0
    private lateinit var job: Job
    private var isFavorite = false

    private lateinit var binding: ActivityDetailJobBinding

    private val detailJobViewModel: DescriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jobIdHome = intent.getIntExtra(EXTRA_DATA, 0)
        jobIdWishlist = intent.getIntExtra(EXTRA_DATA_WISHLIST, 0)
        Log.e("extra home",jobIdHome.toString())
        Log.e("extra wishlist",jobIdWishlist.toString())

        jobId = if (jobIdHome != 0) {
            jobIdHome
        }else {
            jobIdWishlist
        }
        detailJobViewModel.getDescription(jobId)
        observeData()
    }

    private fun observeData() {
        with(binding) {
            detailJobViewModel.detail.observe(this@DescriptionActivity, { results ->
                if (results != null) {
                    when (results) {
                        is Resource.Loading -> binding.content.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.content.progressBar.visibility = View.GONE

                            val detailJob = results.data
                            Log.e("result1", results.toString())
                            Log.e("result2", detailJob.toString())
                            if (detailJob != null) {
                                job = detailJob
                                showDetail(job)
                            }
                        }
                        is Resource.Error -> {
                            binding.content.progressBar.visibility = View.GONE
                            binding.content.viewError.root.visibility = View.VISIBLE
                            binding.content.viewError.ivError
                        }
                    }
                }
            })
        }
    }

    private fun showDetail(detailJob: Job){
        if (detailJob.fullTime){
            binding.tvJobType.text = "Full Time"
        } else if(detailJob.partTime){
            binding.tvJobType.text = "Part Time"
        }
        binding.tvCompanyName.text = detailJob.employerName
        binding.tvJobTitle.text = detailJob.jobTitle
        binding.tvJobLocation.text = detailJob.locationName
        binding.content.tvDetailDescription.text = ConvertHtml(detailJob.jobDescription).fromHtml(detailJob.jobDescription)
        binding.content.tvUrl.text = detailJob.url

        var statusFavorite = jobIdHome == 0

        if (statusFavorite) {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_white
                )
            )
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_not_favorite_white
                )
            )
        }

        binding.fab.setOnClickListener {
            statusFavorite = !statusFavorite
            detailJobViewModel.setWishlistJob(detailJob, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

//    private fun observeFavorite(id: Int) {
//        detailJobViewModel.checkFavorite(id).observe(this, {
//            if (it > 0) {
//                isFavorite = true
//                binding.fab.setImageResource(R.drawable.ic_favorite_white)
//            } else {
//                isFavorite = false
//                binding.fab.setImageResource(R.drawable.ic_favorite_white)
//            }
//        })
//    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }

}
