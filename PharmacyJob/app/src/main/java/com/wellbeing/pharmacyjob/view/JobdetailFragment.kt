package com.wellbeing.pharmacyjob.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wellbeing.pharmacyjob.adapter.JobGridAdapter
import com.wellbeing.pharmacyjob.databinding.FragmentJobdetailBinding
import com.wellbeing.pharmacyjob.model.JobList

class JobdetailFragment : Fragment() {

    private var _binding: FragmentJobdetailBinding? = null

    private lateinit var tvJobID: TextView
    private lateinit var tvBranchName: TextView
    private lateinit var tvLunchArrangement: TextView
    private lateinit var tvParkingOption: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvRatePerMile: TextView

    private lateinit var backButton: Button
    private lateinit var applyButton: Button
    private lateinit var negotiateButton: Button
    private lateinit var withdrawButton: Button

    private lateinit var jobGridView: GridView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobdetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        AppLogger.d("JobdetailFragment", "JobdetailFragment --- onDestroyView")

        _binding = null
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    companion object {
        fun newInstance(item: JobList): JobdetailFragment {
            val fragment = JobdetailFragment()
            val args = Bundle()
            args.putParcelable("item", item) // Assuming ListItem implements Parcelable
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("JobdetailFragment", "JobdetailFragment --- onDestroyView")

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvJobID = binding.dataJobID
        tvBranchName = binding.dataBranchName
        tvLunchArrangement = binding.dataLunchArrangement
        tvParkingOption = binding.dataParkingOption
        tvAddress = binding.dataAddress
        tvRatePerMile = binding.dataRatePerMile
//        backButton = binding.btnBack
        applyButton = binding.btnApply
        negotiateButton = binding.btnNegotiate
        withdrawButton = binding.btnWithdraw

        jobGridView = binding.jobGridView

        // Retrieve the item data passed from the previous fragment
        val item = arguments?.getParcelable<JobList>("item")
val jobId : Long
        val updatedAt: LocalDateTime
        
        item?.let {
            tvJobID.text = it.jobId
            tvBranchName.text = it.branchName
            tvLunchArrangement.text = it.lunchArrangement
            tvParkingOption.text = it.parkingOption
            tvRatePerMile.text = String.format("%.2f", it.ratePerMile)
            tvAddress.text =
                it.branchAddress1 + " \n " + it.branchAddress2 + " \n " + it.branchPostalCode

            val dataList = listOf(
                "Date:\n" + it.jobDate,
                "Time:\n" + it.jobStartTime.take(5) + " - " + it.jobEndTime.take(5),
                "Distance:\n" + String.format("%.2f miles", it.distance),
                "Working Hour:\n" + String.format("%.2f", it.totalWorkHour) + " hours",
                "Hourly Rate:\n£" + String.format("%.2f", it.hourlyRate) + "/hr",
                "Total Paid:\n£" + String.format("%.2f", it.totalPaid),
            )

            // Set up the adapter
            val adapter = JobGridAdapter(requireContext(), dataList)
            jobGridView.adapter = adapter

            jobID = it.jobId
            updatedAt = it.updatedAt
        }

//        backButton.setOnClickListener {
//            onBackButton()
//        }
        applyButton.setOnClickListener {
            applyJob(jobID, updatedAt)
        }
        negotiateButton.setOnClickListener {
            negotiateJob(jobID, updatedAt)
        }
        withdrawButton.setOnClickListener {
            withdrawJob(jobID, updatedAt)
        }
    }

    private fun onBackButton() {
//            findNavController().navigateUp()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun applyJob(Long jobID, LocalDateTime updatedAt) {
            // val request = JobUpdateRequest(
            //     status = "Apply", 
            //     updatedAt = updatedAt
            // )
            // updateJobData(jobID, request)
            // finish()
    }

    private fun negotiateJob() {
//        finish()
    }

    private fun withdrawJob() {
//        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle back button press
                onBackButton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    // private fun updateJobData(id: Long, request: JobUpdateRequest) {
    //     // Initialize the repository and ViewModel
    //     val apiService = RetrofitInstance.api // Your Retrofit API service
    //     val repository = JobUpdateRepository(apiService)

    //     JobUpdateViewModel = ViewModelProvider(this, JobUpdateViewModelFactory(repository))
    //         .get(JobUpdateViewModel::class.java)

    //     JobUpdateViewModel.updateLiveData.observe(this, Observer { result ->
    //         when (result) {
    //             is ApiResult.Success -> {
    //                 // Handle successful login, navigate to the next screen
    //                 Toast.makeText(this, "Update successful!", Toast.LENGTH_SHORT).show()
    //                 AppLogger.d(
    //                     "JobdetailFragment",
    //                     "JobUpdateViewModel.updateLiveData: Login Successful"
    //                 )
    //                 navigateToHomeScreen()
    //             }

    //             is ApiResult.Error -> {
    //                 // Show error message
    //                 Toast.makeText(this, "Update failed: ${response.code()}", Toast.LENGTH_SHORT).show()
    //                 AppLogger.d(
    //                     "JobdetailFragment",
    //                     "JobUpdateViewModel.updateLiveData: Login failed >>> " + result.message
    //                 )
    //             }
    //         }
    //     }
    // }
    
}
