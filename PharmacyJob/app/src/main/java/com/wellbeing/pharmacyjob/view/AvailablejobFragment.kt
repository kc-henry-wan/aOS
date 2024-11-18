package com.wellbeing.pharmacyjob.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.adapter.JobAdapter
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.constant.SpinnerDataProvider
import com.wellbeing.pharmacyjob.databinding.FragmentAvailablejobBinding
import com.wellbeing.pharmacyjob.factory.AvailablejobViewModelFactory
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.repository.AvailablejobRepository
import com.wellbeing.pharmacyjob.viewmodel.AvailablejobViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class AvailablejobFragment : Fragment() {

    private var _binding: FragmentAvailablejobBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var joblistAdapter: JobAdapter
    private val jobList: MutableList<JobList> = mutableListOf()
    private lateinit var startDateText: TextView
    private lateinit var endDateText: TextView
    private val calendar: Calendar = Calendar.getInstance()
    var apiDataSortBy = ""
    private lateinit var availablejobViewModel: AvailablejobViewModel
    private lateinit var sortingSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        AppLogger.d("AvailablejobFragment", "AvailablejobFragment --- onCreateView loaded")

        _binding = FragmentAvailablejobBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        AppLogger.d("AvailablejobFragment", "AvailablejobFragment --- onDestroyView")

        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("AvailablejobFragment", "AvailablejobFragment --- onViewCreated")

        // Initialize TextViews
        startDateText = binding.startDateText
        endDateText = binding.endDateText
        recyclerView = binding.availableJobRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        sortingSpinner = binding.sortingSpinner

        joblistAdapter = JobAdapter(emptyList()) { item ->
            // Navigate to DetailFragment with item data
            val jobDetailFragment = JobdetailFragment.newInstance(item)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, jobDetailFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = joblistAdapter


        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = AvailablejobRepository(apiService)

        availablejobViewModel = ViewModelProvider(this, AvailablejobViewModelFactory(repository))
            .get(AvailablejobViewModel::class.java)

        availablejobViewModel.availablejobLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    // Handle successful login, navigate to the next screen
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.api_get_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    // Switch to Main thread to update UI
                    lifecycleScope.launch {
                        try {
                            withContext(Dispatchers.Main) {
                                jobList.clear()
                                result.data?.data?.content?.forEach { job ->
                                    jobList.add(job)
                                }
                                joblistAdapter.updateData(jobList)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(), getString(R.string.api_get_fail),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                is ApiResult.Error -> {
                    // Show error message
                    Toast.makeText(
                        requireContext(), getString(R.string.api_get_fail) + result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        )

        // Set default dates
        setDefaultDates()
        setupSortingSpinner()
    }

    private fun setDefaultDates() {
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_YEAR, 1) // Tomorrow's date

        val fourDaysLater = Calendar.getInstance()
        fourDaysLater.add(Calendar.DAY_OF_YEAR, 4) // Four days later

        startDateText.text = formatDate(tomorrow)
        endDateText.text = formatDate(fourDaysLater)

        // Set click listeners for date selection
        startDateText.setOnClickListener { showDatePickerDialog(true) }
        endDateText.setOnClickListener { showDatePickerDialog(false) }


    }

    private fun showDatePickerDialog(isStartDate: Boolean) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                if (isStartDate) {
                    startDateText.text = formatDate(selectedDate)
                } else {
                    endDateText.text = formatDate(selectedDate)
                }
                fetchDataFromApi("") // Reload jobs with the updated date range if necessary
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun setupSortingSpinner() {
        // Load spinner items from SpinnerDataProvider
        val spinnerItems = SpinnerDataProvider.getSpinnerItems()

        // Set up an adapter for the spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerItems.map { it.value })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortingSpinner.adapter = adapter

        // Handle selection events
        sortingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinnerItems[position]
                val selectedKey = selectedItem.key  // Get the key
                // Call your API with the selected key
                AppLogger.d(
                    "AvailablejobFragment",
                    "Call fetchDataFromApi on SortingSpinner item selected:" + selectedKey
                )
                fetchDataFromApi(selectedKey)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where no selection is made (optional)
            }
        }
    }

    private fun formatDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-based
        val year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year" // Format as needed (e.g., "15/10/2024")
    }

    private fun fetchDataFromApi(selectedKey: String) {
        val startDate = startDateText.text.toString()
        val endDate = endDateText.text.toString()
        if (selectedKey.isNotEmpty())
            apiDataSortBy = selectedKey

        AppLogger.d(
            "AvailablejobFragment",
            "fetchDataFromApi - Call availablejobViewModel.getAvailableJob: startDate" + startDate + ";endDate" + endDate + ";apiDataSortBy" + apiDataSortBy
        )
        availablejobViewModel.getAvailablejob(startDate, endDate, apiDataSortBy, requireContext())
    }

//    private fun fetchDataFromApi2(selectedKey: String) {
//
//        val startDate = startDateText.text.toString()
//        val endDate = endDateText.text.toString()
//        if (selectedKey.isNotEmpty())
//            apiDataSortBy = selectedKey;
//
////        val apiService = ApiClient.getRetrofit().create(ApiService::class.java)
//        val apiService = RetrofitInstance.api // Your Retrofit API service
//
//        // Launch a coroutine to handle the network call
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                // Perform the search API call here
//                val response = apiService.getAvailableJob(startDate, endDate, apiDataSortBy)
//                AppLogger.d("AvailablejobFragment", "fetchDataFromApi -- " + response.toString());
//                if (response.isSuccessful) {
//                    // Switch to Main thread to update UI
//                    withContext(Dispatchers.Main) {
//                        jobList.clear()
//                        response.body()?.jobs?.forEach { job ->
//                            job.distance = 12.3;
//                            AppLogger.d("AvailablejobFragment", "job -- " + job.toString());
//                            jobList.add(job)
//                        }
//                        joblistAdapter.updateData(jobList)
//                    }
//                } else {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(requireContext(), "Failed to load jobs", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }
//    }
}
