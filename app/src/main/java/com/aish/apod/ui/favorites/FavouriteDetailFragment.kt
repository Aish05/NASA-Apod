package com.aish.apod.ui.favorites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aish.apod.R
import com.aish.apod.databinding.FragmentFavDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Represents class for Favourite Detail
 */
class FavouriteDetailFragment : Fragment() {
    private var _binding: FragmentFavDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: FavouriteDetailFragmentArgs by navArgs()

    private val favouriteDetailsViewModel: FavouriteDetailViewModel by viewModel {
        parametersOf(
            args.favDetails
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_fav_details, container, false)
        val root: View = binding.root
        binding.viewModel = favouriteDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    private fun initializeViews() {
        binding.imgVideoThumbnail.setOnClickListener {
            val videoIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(favouriteDetailsViewModel.pictureData.value?.url)
            }
            requireActivity().startActivity(videoIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}