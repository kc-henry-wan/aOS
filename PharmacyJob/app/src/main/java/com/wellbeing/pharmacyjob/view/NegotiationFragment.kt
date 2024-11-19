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
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.api.SessionManager
import com.wellbeing.pharmacyjob.databinding.FragmentNegotiationBinding
import com.wellbeing.pharmacyjob.factory.NegotiationViewModelFactory
import com.wellbeing.pharmacyjob.model.NegotiationList
import com.wellbeing.pharmacyjob.repository.NegotiationRepository
import com.wellbeing.pharmacyjob.viewmodel.NegotiationViewModel
import com.wellbeing.pharmacynego.adapter.NegotiationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NegotiationFragment : Fragment() {

    private var _binding: FragmentNegotiationBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var negotiationAdapter: NegotiationAdapter
    private val negotiationList: MutableList<NegotiationList> = mutableListOf()
    private lateinit var negotiationViewModel: NegotiationViewModel
    private lateinit var apiResultTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNegotiationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("NegotiationFragment", "onViewCreated")
        // Initialize TextViews
        apiResultTextView = binding.apiResultTextView
        swipeRefreshLayout = binding.swipeRefreshLayout

        recyclerView = binding.negotiationRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        AppLogger.d("NegotiationFragment", "onViewCreated 2")
        negotiationAdapter = NegotiationAdapter(emptyList()) { item ->
            //Navigate to DetailFragment with item data
            val negotiationdetailFragment = NegotiationdetailFragment.newInstance(item)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, negotiationdetailFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = negotiationAdapter

        AppLogger.d("NegotiationFragment", "onViewCreated 3")
        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = NegotiationRepository(apiService)

        negotiationViewModel = ViewModelProvider(this, NegotiationViewModelFactory(repository))
            .get(NegotiationViewModel::class.java)

        AppLogger.d("NegotiationFragment", "onViewCreated 4")
        negotiationViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
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
                                negotiationList.clear()
                                result.data?.data?.content?.forEach { item ->
                                    negotiationList.add(item)
                                }
                                negotiationAdapter.updateData(negotiationList)

                                if (negotiationList.isEmpty()) {
                                    AppLogger.d("NegotiationFragment", "negotiationList.isEmpty")
                                    apiResultTextView.text = "Your negotiation list is empty"
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

        fetchDataFromApi()
    }


    // Function to refresh data on swipe
    private fun refreshData() {
        AppLogger.d("NegotiationFragment", "refreshData")
        // Show loading indicator
        swipeRefreshLayout.isRefreshing = true

        fetchDataFromApi()

        // Stop the refreshing animation
        swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchDataFromApi() {

        val userId = SessionManager.getUserId(requireContext())

        AppLogger.d(
            "NegotiationFragment",
            "fetchDataFromApi - Call negotiationViewModel.getNegotiation: userId:" + userId
        )
        negotiationViewModel.getNegotiation()
    }
}
