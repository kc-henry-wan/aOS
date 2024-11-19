package com.wellbeing.pharmacyjob.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.adapter.JobAdapter
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.databinding.FragmentMyjobBinding
import com.wellbeing.pharmacyjob.factory.MyjobViewModelFactory
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.repository.MyjobRepository
import com.wellbeing.pharmacyjob.viewmodel.MyjobViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyjobFragment : Fragment() {

    private var _binding: FragmentMyjobBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var joblistAdapter: JobAdapter
    private val jobList: MutableList<JobList> = mutableListOf()
    private lateinit var myjobViewModel: MyjobViewModel
    private lateinit var apiResultTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var tabStatus: String
    private lateinit var btnOutstanding: Button
    private lateinit var btnCompleted: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyjobBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TextViews
        apiResultTextView = binding.apiResultTextView
        swipeRefreshLayout = binding.swipeRefreshLayout
        btnOutstanding = binding.btnOutstanding
        btnCompleted = binding.btnCompleted

        recyclerView = binding.myjobRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        joblistAdapter = JobAdapter(emptyList()) { item ->
            // Navigate to DetailFragment with item data
            val jobdetailFragment = JobdetailFragment.newInstance(item)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, jobdetailFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = joblistAdapter

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = MyjobRepository(apiService)

        myjobViewModel = ViewModelProvider(this, MyjobViewModelFactory(repository))
            .get(MyjobViewModel::class.java)

        myjobViewModel.myjobLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    Toast.makeText(
                        requireContext(), getString(R.string.api_get_success),
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

                                if (jobList.isEmpty()) {
                                    AppLogger.d("MyjobFragment", "jobList.isEmpty")
                                    apiResultTextView.text = "Your Job list is empty"
                                } else {
                                    apiResultTextView.setHeight(0)
                                }

                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                apiResultTextView.text = getString(R.string.api_get_fail)
                            }
                        }
                    }
                }

                is ApiResult.Error -> {
                    // Show error message
                    apiResultTextView.text = getString(R.string.api_get_fail)
                }
            }
        }
        )

        swipeRefreshLayout.setOnRefreshListener {
            // Refresh data when user swipes down
            refreshData()
        }

        btnOutstanding.setOnClickListener {
            // Handle 'Outstanding' tab selection
            tabStatus = getString(R.string.job_status_assigned)
            btnOutstanding.isSelected = true
            btnCompleted.isSelected = false
            fetchDataFromApi()
            // Show Outstanding content
        }

        btnCompleted.setOnClickListener {
            // Handle 'Completed' tab selection
            tabStatus = getString(R.string.job_status_completed)
            btnOutstanding.isSelected = false
            btnCompleted.isSelected = true
            fetchDataFromApi()
            // Show Completed content
        }

        btnOutstanding.callOnClick()
    }

    // Function to refresh data on swipe
    private fun refreshData() {
        AppLogger.d("MyjobFragment", "refreshData")
        // Show loading indicator
        swipeRefreshLayout.isRefreshing = true

        fetchDataFromApi()

        // Stop the refreshing animation
        swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchDataFromApi() {
        myjobViewModel.getMyJob(tabStatus)
    }


}
