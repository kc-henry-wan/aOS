package com.wellbeing.pharmacyjob.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import com.wellbeing.pharmacyjob.factory.NegotiateAddViewModelFactory
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.model.NegotiateAddRequest
import com.wellbeing.pharmacyjob.model.UpdateJobRequest
import com.wellbeing.pharmacyjob.repository.JobDetailRepository
import com.wellbeing.pharmacyjob.repository.JobUpdateRepository
import com.wellbeing.pharmacyjob.repository.NegotiateAddRepository
import com.wellbeing.pharmacyjob.viewmodel.JobDetailViewModel
import com.wellbeing.pharmacyjob.viewmodel.JobUpdateViewModel
import com.wellbeing.pharmacyjob.viewmodel.NegotiateAddViewModel
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
    private lateinit var negotiateAddViewModel: NegotiateAddViewModel
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
        var totalWorkHour: Double = 0.0

        AppLogger.d("JobdetailFragment", "2")

        item?.let {
            jobId = it.jobId
            distance = String.format(Locale.UK, "%.2f miles", it.distance)
            AppLogger.d("JobdetailFragment", "3:" + distance)

        }

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = JobDetailRepository(apiService)
        val repositoryUpdate = JobUpdateRepository(apiService)
        val repositoryNego = NegotiateAddRepository(apiService)

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
                    totalWorkHour = job?.totalWorkHour?.toDouble()!!
                    AppLogger.d("JobdetailFragment", "7:" + updatedAt)
                    tvJobID.text = job?.jobRef
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

                    if (job?.status == "Open") {
                        applyButton.visibility = View.VISIBLE
                        negotiateButton.visibility = View.VISIBLE
                        withdrawButton.visibility = View.GONE
                    } else if (job?.status == "Assigned") {
                        applyButton.visibility = View.GONE
                        negotiateButton.visibility = View.GONE
                        withdrawButton.visibility = View.VISIBLE
                    }
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

        negotiateAddViewModel =
            ViewModelProvider(this, NegotiateAddViewModelFactory(repositoryNego))
                .get(NegotiateAddViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        negotiateAddViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
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
            showConfirmationDialog(jobId, getString(R.string.job_status_apply), updatedAt, 0.0)
        }
        negotiateButton.setOnClickListener {
            showConfirmationDialog(
                jobId, getString(R.string.negotiate_status_new), updatedAt, totalWorkHour
            )
        }
        withdrawButton.setOnClickListener {
            showConfirmationDialog(
                jobId, getString(R.string.job_status_withdraw), updatedAt, 0.0
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


    private fun showConfirmationDialog(
        id: String,
        actionMode: String,
        updatedAt: String,
        totalWorkHour: Double
    ) {

        // Inflate the custom dialog layout
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_confirmation, null)

        // Create the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Set up dialog views
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tvDialogMessage)
        val dataPurposedRate = dialogView.findViewById<EditText>(R.id.dataPurposedRate)
        val dataPurposedTotal = dialogView.findViewById<TextView>(R.id.dataPurposedTotal)
        var purposedTotal: Double = 0.0

        // Set custom message or title dynamically (optional)
        if (actionMode == getString(R.string.job_status_apply)) {
            tvDialogMessage.text = getString(R.string.job_apply_confirmation)
            dataPurposedRate.visibility = View.GONE
            dataPurposedTotal.visibility = View.GONE
        } else if (actionMode == getString(R.string.job_status_withdraw)) {
            tvDialogMessage.text = getString(R.string.job_withdraw_confirmation)
            dataPurposedRate.visibility = View.GONE
            dataPurposedTotal.visibility = View.GONE
        } else if (actionMode == getString(R.string.negotiate_status_new)) {
            tvDialogMessage.text = getString(R.string.negotiate_new_confirmation)
            dataPurposedRate.visibility = View.VISIBLE
            dataPurposedTotal.visibility = View.VISIBLE
        }

        // Handle button clicks
        btnCancel.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        btnConfirm.setOnClickListener {
            if (actionMode == getString(R.string.job_status_apply)) {
                dialog.dismiss() // Close the dialog
                jobUpdateViewModel.updateJobStatus(
                    id, UpdateJobRequest("8", actionMode, updatedAt)
                )
            } else if (actionMode == getString(R.string.job_status_withdraw)) {
                dialog.dismiss() // Close the dialog
                jobUpdateViewModel.updateJobStatus(
                    id, UpdateJobRequest("8", actionMode, updatedAt)
                )
            } else if (actionMode == getString(R.string.negotiate_status_new)) {
                if (purposedTotal > 0) {
                    dialog.dismiss() // Close the dialog
                    negotiateAddViewModel.addNegotiation(
                        NegotiateAddRequest(
                            "8", actionMode, dataPurposedRate.text.toString(),
                            purposedTotal.toString()
                        )
                    )
                } else {
                    dataPurposedTotal.text = getString(R.string.negotiate_new_error)
                    dataPurposedTotal.setTextColor(Color.RED)
                }
            }
        }

        // Set up TextWatcher to calculate the result immediately when user types
        dataPurposedRate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputValue = s.toString()
                if (inputValue.isNotEmpty()) {
                    try {
                        val inputDouble = inputValue.toDouble()
                        purposedTotal = inputDouble * totalWorkHour
                        dataPurposedTotal.text =
                            getString(R.string.negotiate_new_input) + purposedTotal
                        dataPurposedTotal.setTextColor(Color.GRAY)
                    } catch (e: NumberFormatException) {
                        dataPurposedTotal.text = ""
                        purposedTotal = 0.0
                    }
                } else {
                    dataPurposedTotal.text = ""
                    purposedTotal = 0.0
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Show the dialog
        dialog.show()
    }
}
