package com.wellbeing.pharmacyjob.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.databinding.FragmentUploadBinding
import com.wellbeing.pharmacyjob.factory.MyDocViewModelFactory
import com.wellbeing.pharmacyjob.repository.MyDocRepository
import com.wellbeing.pharmacyjob.viewmodel.MyDocViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private lateinit var idImageView: ImageView
    private lateinit var certImageView: ImageView
    private lateinit var licenseImageView: ImageView
    private lateinit var myDocViewModel: MyDocViewModel
    private lateinit var apiResultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("UploadFragment", "UploadFragment --- onViewCreated")

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        idImageView = binding.idImageView
        certImageView = binding.certImageView
        licenseImageView = binding.licenseImageView
        apiResultTextView = binding.apiResultTextView

        binding.uploadIdButton.setOnClickListener {
            openGalleryForResult(REQUEST_ID)
        }

        binding.uploadCertButton.setOnClickListener {
            openGalleryForResult(REQUEST_CERT)
        }

        binding.uploadLicenseButton.setOnClickListener {
            openGalleryForResult(REQUEST_LICENSE)
        }

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = MyDocRepository(apiService)

        AppLogger.d("UploadFragment", "2")
        myDocViewModel = ViewModelProvider(this, MyDocViewModelFactory(repository))
            .get(MyDocViewModel::class.java)

        AppLogger.d("UploadFragment", "3")
        myDocViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("UploadFragment", "4")
                    Toast.makeText(
                        requireContext(), getString(R.string.api_get_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    // Switch to Main thread to update UI
                    lifecycleScope.launch {
                        try {
                            AppLogger.d("UploadFragment", "5")
                            withContext(Dispatchers.Main) {
                                result.data?.data?.content?.forEach { doc ->

                                    //TODO:Add logic to handle document downlaod
                                    AppLogger.d("UploadFragment", "doc:" + doc.toString())
                                }
                            }
                        } catch (e: Exception) {
                            AppLogger.d("UploadFragment", "6")
                            withContext(Dispatchers.Main) {
                                apiResultTextView.text = getString(R.string.api_get_fail)
                            }
                        }
                    }
                }

                is ApiResult.Error -> {
                    AppLogger.d("UploadFragment", "7")
                    // Show error message
                    apiResultTextView.text = getString(R.string.api_get_fail)
                }
            }
        }
        )
        myDocViewModel.getMyDocList()
    }

    private fun openGalleryForResult(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            when (requestCode) {
                REQUEST_ID -> idImageView.setImageURI(imageUri)
                REQUEST_CERT -> certImageView.setImageURI(imageUri)
                REQUEST_LICENSE -> licenseImageView.setImageURI(imageUri)
            }
        }
    }

    companion object {
        const val REQUEST_ID = 1
        const val REQUEST_CERT = 2
        const val REQUEST_LICENSE = 3
    }

    private fun onBackButton() {
//            findNavController().navigateUp()
        requireActivity().supportFragmentManager.popBackStack()
    }
}
