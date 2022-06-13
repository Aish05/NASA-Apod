package com.aish.apod.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aish.apod.R
import com.aish.apod.common.OnClickListener
import com.aish.apod.common.SafeObserver
import com.aish.apod.databinding.FragmentFavouriteBinding
import com.aish.apod.model.PictureData
import com.aish.apod.ui.home.HomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Represents class for Favourites
 */
class FavouritesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val favouriteViewModel: FavouritesViewModel by viewModel()
    private lateinit var favAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)
        val root: View = binding.root
        binding.viewModel = favouriteViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeObservers()
    }

    override fun onItemClick(pictureData: PictureData) {
        navigateToFavDetailsFragment(pictureData)
    }

    override fun onDeleteFav(pictureData: PictureData) {
        favouriteViewModel.deleteFav(pictureData)
    }

    private fun initializeViews() {
        favAdapter = FavouritesAdapter(context, this)
        binding.rvFav.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvFav.adapter = favAdapter
        binding.rvFav.isNestedScrollingEnabled = false
    }

    private fun initializeObservers() {
        favouriteViewModel.favList.observe(viewLifecycleOwner, SafeObserver {
            favAdapter.setPictureData(it)
        })
    }

    private fun navigateToFavDetailsFragment(pictureData: PictureData) {
        findNavController().navigate(HomeFragmentDirections.actionToFavDetails(pictureData))
    }
}