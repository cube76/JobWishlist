package com.mqa.jobwishlist.wishlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.mqa.jobwishlist.core.ui.JobAdapter
import com.mqa.jobwishlist.core.utils.Constant.Companion.DEEPLINK_WISHLIST
import com.mqa.jobwishlist.core.utils.Constant.Companion.DELAY_TRANSITION
import com.mqa.jobwishlist.di.WishlistModuleDependencies
import com.mqa.jobwishlist.ui.desc.DescriptionActivity
import com.mqa.jobwishlist.wishlist.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class WishlistFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: WishlistViewModel by viewModels{
        factory
    }
    private lateinit var jobAdapter: JobAdapter

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerWishlistComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().application,
                    WishlistModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        enterTransition = MaterialFadeThrough().apply {
            duration = DELAY_TRANSITION
        }

        exitTransition = MaterialElevationScale(false).apply {
            duration = DELAY_TRANSITION
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = DELAY_TRANSITION
        }
        jobAdapter = JobAdapter()
    }


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            jobAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DescriptionActivity::class.java)
                intent.putExtra(DescriptionActivity.EXTRA_DATA_WISHLIST, selectedData.jobId)
                startActivity(intent)
            }

            favoriteViewModel.wishlistJob.observe(viewLifecycleOwner, { dataTourism ->
                jobAdapter.setData(dataTourism)
                binding.viewEmpty.root.visibility = if (dataTourism.isNotEmpty()) View.GONE else View.VISIBLE
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
