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
import com.wellbeing.pharmacyjob.databinding.FragmentMyfavoriteBinding
import com.wellbeing.pharmacyjob.factory.MyfavoriteViewModelFactory
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.repository.MyfavoriteRepository
import com.wellbeing.pharmacyjob.utils.FavoriteManager
import com.wellbeing.pharmacyjob.viewmodel.MyfavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyfavoriteFragment : Fragment() {

    private var _binding: FragmentMyfavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var joblistAdapter: JobAdapter
    private val jobList: MutableList<JobList> = mutableListOf()
    private lateinit var myfavoriteViewModel: MyfavoriteViewModel
    private lateinit var apiResultTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AppLogger.d("MyfavoriteFragment", "MyfavoriteFragment --- onCreateView loaded")

        _binding = FragmentMyfavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        AppLogger.d("MyfavoriteFragment", "MyfavoriteFragment --- onDestroyView")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("MyfavoriteFragment", "MyfavoriteFragment --- onDestroyView")

        // Initialize TextViews
        apiResultTextView = binding.apiResultTextView
        swipeRefreshLayout = binding.swipeRefreshLayout

        recyclerView = binding.myfavoriteRecycler
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
        val repository = MyfavoriteRepository(apiService)

        myfavoriteViewModel = ViewModelProvider(this, MyfavoriteViewModelFactory(repository))
            .get(MyfavoriteViewModel::class.java)

        myfavoriteViewModel.myfavoriteLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "getMyFavoriteJob Successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    AppLogger.d(
                        "MyfavoriteFragment",
                        "myfavoriteViewModel.myfavoriteLiveData: getMyFavoriteJob Successful"
                    )
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
                                    AppLogger.d("MyfavoriteFragment", "jobList.isEmpty")
                                    apiResultTextView.text = "Your My Favorite is empty"
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
                        "MyfavoriteFragment",
                        "LiveData: getMyFavoriteJob failed >>> " + result.message
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
            "MyfavoriteFragment",
            "refreshData"
        )
        // Show loading indicator
        swipeRefreshLayout.isRefreshing = true

        fetchDataFromApi()

        // Stop the refreshing animation
        swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchDataFromApi() {
        val favoriteManager = FavoriteManager(requireContext())
        val favoriteIds: Set<String> = favoriteManager.getFavorites()
        val strfavoriteIds = favoriteIds.toString()

        AppLogger.d(
            "MyfavoriteFragment",
            "fetchDataFromApi - Call myfavoriteViewModel.getMyFavoriteJob: favoriteIds:" + strfavoriteIds
        )
        myfavoriteViewModel.getMyFavoriteJob(strfavoriteIds, requireContext())
    }

}
