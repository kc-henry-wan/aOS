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
import androidx.navigation.fragment.findNavController
import com.wellbeing.pharmacyjob.adapter.JobGridAdapter
import com.wellbeing.pharmacyjob.databinding.FragmentJobdetailBinding
import com.wellbeing.pharmacyjob.model.JobList

class JobdetailFragment : Fragment() {

    private var _binding: FragmentJobdetailBinding? = null

    private lateinit var tvJobID: TextView
    private lateinit var tvBranchName: TextView
    private lateinit var tvJobDate: TextView
    private lateinit var tvJobTime: TextView
    private lateinit var tvHourlyRate: TextView
    private lateinit var tvTotalWorkHour: TextView
    private lateinit var tvTotalPaid: TextView
    private lateinit var tvLunchArrangement: TextView
    private lateinit var tvParkingOption: TextView
    private lateinit var tvAddress: TextView

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

        AppLogger.d("JobdetailFragment","JobdetailFragment --- onDestroyView")

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

        AppLogger.d("JobdetailFragment","JobdetailFragment --- onDestroyView")

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvJobID = binding.dataJobID
        tvBranchName = binding.dataBranchName
        tvJobDate = binding.dataJobDate
        tvJobTime = binding.dataJobTime
        tvHourlyRate = binding.dataHourlyRate
        tvTotalWorkHour = binding.dataTotalWorkHour
        tvTotalPaid = binding.dataTotalPaid
        tvLunchArrangement = binding.dataLunchArrangement
        tvParkingOption = binding.dataParkingOption
        tvAddress = binding.dataAddress
//        backButton = binding.btnBack
        applyButton = binding.btnApply
        negotiateButton = binding.btnNegotiate
        withdrawButton = binding.btnWithdraw

        jobGridView=binding.jobGridView

        // Retrieve the item data passed from the previous fragment
        val item = arguments?.getParcelable<JobList>("item")
        item?.let {
            tvJobID.text = it.jobID
            tvBranchName.text = it.branchName
            tvJobDate.text = it.jobDate
            tvJobTime.text = it.jobStartTime + "-" + it.jobEndTime
            tvHourlyRate.text = it.hourlyRate.toString()
            tvTotalWorkHour.text = it.totalPaidHour.toString()
            tvTotalPaid.text = it.totalPaid.toString()
            tvLunchArrangement.text = it.lunchArrangement
            tvParkingOption.text = it.parkingOption
            tvAddress.text = it.address + " " + it.postalCode

            val dataList = listOf(
                "Date:\n"+it.jobDate,
                "Time:\n"+it.jobStartTime + " - " + it.jobEndTime,
                "Distance:\n"+it.distance.toString()+" miles",
                "Working Hour:\n"+it.totalPaidHour.toString()+" hours",
                "Hourly Rate:\n£"+it.hourlyRate.toString()+"/hr",
                "Total Paid:\n£"+it.totalPaid.toString(),
            )
            tvJobDate.height = 0
            tvJobTime.height = 0
            tvHourlyRate.height = 0
            tvTotalPaid.height = 0
            tvTotalWorkHour.height = 0

            binding.labelJobDate.height = 0
            binding.labelJobTime.height = 0
            binding.labelHourlyRate.height = 0
            binding.labelTotalPaid.height = 0
            binding.labelTotalWorkHour.height = 0

            // Set up the adapter
            val adapter = JobGridAdapter(requireContext(), dataList)
            jobGridView.adapter = adapter
        }

//        backButton.setOnClickListener {
//            onBackButton()
//        }
        applyButton.setOnClickListener {
            applyJob()
        }
        negotiateButton.setOnClickListener {
            negotiateJob()
        }
        withdrawButton.setOnClickListener {
            withdrawJob()
        }
    }

    private fun onBackButton() {
//            findNavController().navigateUp()
        requireActivity().supportFragmentManager.popBackStack()
    }
    private fun applyJob() {
//        finish()
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
}