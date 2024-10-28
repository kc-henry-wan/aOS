package com.wellbeing.pharmacyjob.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.wellbeing.pharmacyjob.api.SessionManager
import com.wellbeing.pharmacyjob.databinding.FragmentMyjobBinding
import com.wellbeing.pharmacyjob.factory.MyfavoriteViewModelFactory
import com.wellbeing.pharmacyjob.factory.MyjobViewModelFactory
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.repository.MyfavoriteRepository
import com.wellbeing.pharmacyjob.repository.MyjobRepository
import com.wellbeing.pharmacyjob.viewmodel.MyfavoriteViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AppLogger.d("MyjobFragment", "MyjobFragment --- onCreateView loaded")

        _binding = FragmentMyjobBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        AppLogger.d("MyjobFragment","MyjobFragment --- onDestroyView")
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("MyjobFragment","MyjobFragment --- onDestroyView")

        // Initialize TextViews
        apiResultTextView = binding.apiResultTextView
        swipeRefreshLayout = binding.swipeRefreshLayout

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
                        requireContext(),
                        "getMyJob Successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    AppLogger.d(
                        "MyjobFragment",
                        "myjobViewModel.myjobLiveData: getMyjobJob Successful"
                    )
                    // Switch to Main thread to update UI
                    lifecycleScope.launch {
                        try {
                            withContext(Dispatchers.Main) {
                                jobList.clear()
                                result.data?.jobs?.forEach { job ->
                                    job.distance = 1.3;
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
                                apiResultTextView.text = "Failed to load data"
                            }
                        }
                    }
                }

                is ApiResult.Error -> {
                    // Show error message
                    apiResultTextView.text = "Failed to load data"
                    AppLogger.d(
                        "MyjobFragment",
                        "LiveData: getMyjobJob failed >>> " + result.message
                    )
                }
            }
        }
        )

        swipeRefreshLayout.setOnRefreshListener {
            // Refresh data when user swipes down
            refreshData()
        }

        fetchDataFromApi()
    }

    // Function to refresh data on swipe
    private fun refreshData() {
        AppLogger.d(
            "MyjobFragment",
            "refreshData"
        )
        // Show loading indicator
        swipeRefreshLayout.isRefreshing = true

        fetchDataFromApi()

        // Stop the refreshing animation
        swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchDataFromApi() {

        val userId =SessionManager.getUserId(requireContext())

        AppLogger.d(
            "MyjobFragment",
            "fetchDataFromApi - Call myjobViewModel.getMyJob: userId:" + userId
        )
        myjobViewModel.getMyJob(userId, requireContext())
    }
}