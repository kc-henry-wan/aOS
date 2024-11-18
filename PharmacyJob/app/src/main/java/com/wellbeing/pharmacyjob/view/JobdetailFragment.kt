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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.adapter.JobGridAdapter
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.databinding.FragmentJobdetailBinding
import com.wellbeing.pharmacyjob.factory.JobDetailViewModelFactory
import com.wellbeing.pharmacyjob.factory.JobUpdateViewModelFactory
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.model.UpdateJobRequest
import com.wellbeing.pharmacyjob.repository.JobDetailRepository
import com.wellbeing.pharmacyjob.repository.JobUpdateRepository
import com.wellbeing.pharmacyjob.viewmodel.JobDetailViewModel
import com.wellbeing.pharmacyjob.viewmodel.JobUpdateViewModel
import java.util.Locale

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

    private lateinit var apiResultTextView: TextView
    private lateinit var jobDetailViewModel: JobDetailViewModel
    private lateinit var jobUpdateViewModel: JobUpdateViewModel
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

        AppLogger.d("JobdetailFragment", "JobdetailFragment --- onViewCreated")

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
        apiResultTextView = binding.apiResultTextView

        AppLogger.d("JobdetailFragment", "1")

        // Retrieve the item data passed from the previous fragment
        val item = arguments?.getParcelable<JobList>("item")
        var jobId: String = ""
        var updatedAt: String = ""
        var distance: String = ""

        AppLogger.d("JobdetailFragment", "2")

        item?.let {
            jobId = it.jobId
            distance = String.format(Locale.UK, "%.2f miles", it.distance)
            AppLogger.d("JobdetailFragment", "3:" + distance)

        }

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = JobDetailRepository(apiService)
        val repositoryUpdate = JobUpdateRepository(apiService)

        AppLogger.d("JobdetailFragment", "4")
        jobDetailViewModel = ViewModelProvider(this, JobDetailViewModelFactory(repository))
            .get(JobDetailViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        jobDetailViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("JobdetailFragment", "5:" + result)

                    val job = result.data?.data

                    AppLogger.d("JobdetailFragment", "6:")
                    updatedAt = job?.updatedAt.toString()
                    AppLogger.d("JobdetailFragment", "7:" + updatedAt)
                    tvJobID.text = job?.jobId
                    tvBranchName.text = job?.branchName
                    tvLunchArrangement.text = job?.lunchArrangement
                    tvParkingOption.text = job?.parkingOption
                    tvRatePerMile.text = String.format(Locale.UK, "%.2f", job?.ratePerMile)
//                    tvAddress.text =
//                        job?.branchAddress1 + " \n " + job?.branchAddress2 + " \n " + job?.branchPostalCode
                    tvAddress.text = getString(
                        R.string.address_format,
                        job?.branchAddress1 ?: "",
                        job?.branchAddress2 ?: "",
                        job?.branchPostalCode ?: ""
                    )
                    val dataList = listOf(
                        "Date:\n" + job?.jobDate,
                        "Time:\n" + job?.jobStartTime?.take(5) + " - " + job?.jobEndTime?.take(5),
                        "Distance:\n$distance",
                        "Working Hour:\n" + job?.totalWorkHour + " hours",
                        "Hourly Rate:\n£" + job?.hourlyRate + "/hr",
                        "Total Paid:\n£" + job?.totalPaid,
                    )

                    // Set up the adapter
                    val adapter = JobGridAdapter(requireContext(), dataList)
                    jobGridView.adapter = adapter

                }

                is ApiResult.Error -> {
                    AppLogger.d("JobdetailFragment", "API fail")
                    apiResultTextView.text = getString(R.string.api_get_fail)
                }
            }
        })

        jobUpdateViewModel = ViewModelProvider(this, JobUpdateViewModelFactory(repositoryUpdate))
            .get(JobUpdateViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        jobUpdateViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    apiResultTextView.text = getString(R.string.api_update_success)
                    onBackButton()
                }

                is ApiResult.Error -> {
                    apiResultTextView.text = getString(R.string.api_update_fail)
                }
            }
        })

//        backButton.setOnClickListener {
//            onBackButton()
//        }
        applyButton.setOnClickListener {
            jobUpdateViewModel.updateJobStatus(
                jobId, UpdateJobRequest("8", getString(R.string.job_status_apply), updatedAt)
            )
        }
        negotiateButton.setOnClickListener {
            //Goto negotitaion page
        }
        withdrawButton.setOnClickListener {
            jobUpdateViewModel.updateJobStatus(
                jobId, UpdateJobRequest("8", getString(R.string.job_status_withdraw), updatedAt)
            )
        }

        jobDetailViewModel.getJobDetail(jobId)
    }

    private fun onBackButton() {
//            findNavController().navigateUp()
        requireActivity().supportFragmentManager.popBackStack()
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
}
