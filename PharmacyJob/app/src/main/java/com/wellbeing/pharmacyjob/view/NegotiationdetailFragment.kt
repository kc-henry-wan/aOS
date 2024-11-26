package com.wellbeing.pharmacyjob.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
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
import com.wellbeing.pharmacyjob.databinding.FragmentNegotiationdetailBinding
import com.wellbeing.pharmacyjob.factory.NegotiateUpdateViewModelFactory
import com.wellbeing.pharmacyjob.model.NegotiateUpdateRequest
import com.wellbeing.pharmacyjob.model.NegotiationList
import com.wellbeing.pharmacyjob.repository.NegotiateUpdateRepository
import com.wellbeing.pharmacyjob.viewmodel.NegotiateUpdateViewModel
import java.util.Locale

class NegotiationdetailFragment : Fragment() {

    private var _binding: FragmentNegotiationdetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvJobID: TextView
    private lateinit var tvBranchName: TextView
    private lateinit var tvLunchArrangement: TextView
    private lateinit var tvParkingOption: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvRatePerMile: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvOriginalRate: TextView
    private lateinit var tvProposedRate: TextView
    private lateinit var tvCounterRate: TextView
    private lateinit var ivArrow2: ImageView

    private lateinit var backButton: Button
    private lateinit var acceptButton: Button

    private lateinit var jobGridView: GridView

    private lateinit var apiResultTextView: TextView
    private lateinit var negotiateUpdateViewModel: NegotiateUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNegotiationdetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        AppLogger.d("NegotiatedetailFragment", "NegotiatedetailFragment --- onDestroyView")

        _binding = null
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    companion object {
        fun newInstance(item: NegotiationList): NegotiationdetailFragment {
            val fragment = NegotiationdetailFragment()
            val args = Bundle()
            args.putParcelable("item", item) // Assuming ListItem implements Parcelable
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("NegotiatedetailFragment", "NegotiatedetailFragment --- onViewCreated")

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvJobID = binding.dataJobID
        tvBranchName = binding.dataBranchName
        tvLunchArrangement = binding.dataLunchArrangement
        tvParkingOption = binding.dataParkingOption
        tvAddress = binding.dataAddress
        tvRatePerMile = binding.dataRatePerMile
        tvStatus = binding.dataStatus
        tvOriginalRate = binding.dataOriginalRate
        tvProposedRate = binding.dataProposedRate
        tvCounterRate = binding.dataCounterRate
        ivArrow2 = binding.arrow2
//        backButton = binding.btnBack
        acceptButton = binding.btnAccept

        jobGridView = binding.jobGridView
        apiResultTextView = binding.apiResultTextView

        AppLogger.d("NegotiatedetailFragment", "1")

        // Retrieve the item data passed from the previous fragment
        val item = arguments?.getParcelable<NegotiationList>("item")
        var negotiationId: String = ""
        var updatedAt: String = ""
        var status: String = ""
        var jobUpdatedAt: String = ""

        AppLogger.d("NegotiatedetailFragment", "2")

        item?.let {
            tvJobID.text = it.jobRef
            tvBranchName.text = it.branchName
            tvAddress.text = getString(
                R.string.address_format,
                it.branchAddress1,
                it.branchAddress2 ?: "",
                it.branchPostalCode
            )

            tvOriginalRate.text =
                "Original:\n Hourly Rate: £" + it.originalHourlyRate + "/hr \n Total(£): " + it.originalTotalPaid
            tvProposedRate.text =
                "Your purposed:\n Hourly Rate: £" + it.purposedHourlyRate + "/hr \n Total(£): " + it.purposedTotalPaid

            if (it.counterHourlyRate > 0) {
                tvCounterRate.text =
                    "Admin counter purposed:\n Hourly Rate: £" + it.counterHourlyRate + "/hr \n Total(£): " + it.counterTotalPaid
            } else {
                tvCounterRate.visibility = View.GONE
                ivArrow2.visibility = View.GONE
            }
            val dataList = listOf(
                "Date:\n" + it.jobDate,
                "Time:\n" + it.jobStartTime?.take(5) + " - " + it.jobEndTime?.take(5),
                "Working Hour:\n" + it.totalWorkHour + " hours"
            )
            tvLunchArrangement.text = it.lunchArrangement
            tvParkingOption.text = it.parkingOption
            tvRatePerMile.text = String.format(Locale.UK, "%.2f", it.ratePerMile)
            tvStatus.text = it.status
            negotiationId = it.negotiationId
            updatedAt = it.updatedAt
            status = it.status
            jobUpdatedAt = it.jobUpdatedAt
            AppLogger.d("NegotiatedetailFragment", "3:")

            // Set up the adapter
            val adapter = JobGridAdapter(requireContext(), dataList)
            jobGridView.adapter = adapter
        }

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = NegotiateUpdateRepository(apiService)

        negotiateUpdateViewModel =
            ViewModelProvider(this, NegotiateUpdateViewModelFactory(repository))
                .get(NegotiateUpdateViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        negotiateUpdateViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
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
        acceptButton.setOnClickListener {
            showConfirmationDialog(
                negotiationId,
                getString(R.string.negotiation_action_accept),
                updatedAt,
                jobUpdatedAt
            )
        }

        if (status == getString(R.string.negotiation_status_counter)) {
            acceptButton.visibility = View.VISIBLE
        } else {
            acceptButton.visibility = View.GONE
        }

        if (status == getString(R.string.negotiation_status_new)) {
            tvStatus.setTextColor(Color.BLUE)
            tvStatus.text = "New (pending Approval)"
        } else if (status == getString(R.string.negotiation_status_admin_accept)) {
            tvStatus.setTextColor(Color.GREEN)
        } else if (status == getString(R.string.negotiation_status_counter)) {
            tvStatus.setTextColor(Color.BLUE)
        } else if (status == getString(R.string.negotiation_status_pharmacist_accept)) {
            tvStatus.setTextColor(Color.GREEN)
        } else {
            tvStatus.setTextColor(Color.RED)
        }
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
        id: String, actionMode: String, updatedAt: String, jobUpdatedAt: String
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

        // Set custom message or title dynamically (optional)
        dataPurposedRate.visibility = View.GONE
        dataPurposedTotal.visibility = View.GONE

        if (actionMode == getString(R.string.negotiation_action_accept)) {
            tvDialogMessage.text = getString(R.string.negotiation_accept_confirmation)
        }

        // Handle button clicks
        btnCancel.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        btnConfirm.setOnClickListener {
            if (actionMode == getString(R.string.negotiation_action_accept)) {
                dialog.dismiss() // Close the dialog
                negotiateUpdateViewModel.updateNegotiation(
                    id, NegotiateUpdateRequest("8", actionMode, updatedAt, jobUpdatedAt)
                )
            }
        }

        // Show the dialog
        dialog.show()
    }
}
