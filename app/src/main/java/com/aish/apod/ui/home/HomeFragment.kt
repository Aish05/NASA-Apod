package com.aish.apod.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aish.apod.R
import com.aish.apod.common.SafeObserver
import com.aish.apod.databinding.FragmentHomeBinding
import com.aish.apod.util.getCurrentDate
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Represents Picture of the day home fragment
 */
class HomeFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private val connectivityManager: ConnectivityManager by inject()

    var day = 0
    var month: Int = 0
    var year: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setIsConnected(isConnected())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root: View = binding.root
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeObservers()
        homeViewModel.fetchPictureData(getCurrentDate())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.day = dayOfMonth
        this.month = month
        this.year = year

        homeViewModel.fetchPictureData("$year-${month+1}-$dayOfMonth")
    }

    private fun initializeViews() {
        binding.ivDateChange.setOnClickListener {
            openDatePicker()
        }
        binding.imgVideoThumbnail.setOnClickListener {
            val videoIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(homeViewModel.pictureData.value?.url)
            }
            requireActivity().startActivity(videoIntent)
        }
    }
    private fun initializeObservers() {
        homeViewModel.showToast.observe(viewLifecycleOwner, SafeObserver{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun isConnected(): Boolean {
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun openDatePicker() {
        if (day == 0) {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
        }
        val datePickerDialog =
            DatePickerDialog(requireContext(),this, year, month, day)

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.setOnShowListener {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
        datePickerDialog.show()
    }
}