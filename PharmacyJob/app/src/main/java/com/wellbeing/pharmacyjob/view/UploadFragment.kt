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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.adapter.ImageAdapter
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.databinding.FragmentUploadBinding
import com.wellbeing.pharmacyjob.factory.MyDocViewModelFactory
import com.wellbeing.pharmacyjob.model.UserDoc
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val myList: MutableList<UserDoc> = mutableListOf()

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

        recyclerView = binding.mylistRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        imageAdapter = ImageAdapter(emptyList()) { item ->
            showFullScreenImageDialog(item.imageId)
            // Navigate to DetailFragment with item data
//            val jobdetailFragment = JobdetailFragment.newInstance(item)
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment_activity_main, jobdetailFragment)
//                .addToBackStack(null)
//                .commit()
        }
        recyclerView.adapter = imageAdapter

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

        myDocViewModel = ViewModelProvider(this, MyDocViewModelFactory(repository))
            .get(MyDocViewModel::class.java)

        myDocViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
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
                                myList.clear()
                                result.data?.data?.content?.forEach { doc ->
                                    myList.add(doc)
                                    AppLogger.d("UploadFragment", "doc:" + doc.toString())
                                }
                                imageAdapter.updateData(myList)
                            }
                            if (myList.isEmpty()) {
                                apiResultTextView.text = "Your list is empty"
                            } else {
                                apiResultTextView.setHeight(0)
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

    private fun showFullScreenImageDialog(imageId: String) {

        // Inflate the custom dialog layout
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fullscreen_image, null)

        // Create the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        var ivFullImg = dialogView.findViewById<ImageView>(R.id.imageViewFullScreen)
        var ivClose = dialogView.findViewById<ImageView>(R.id.close)

//        ConfigReader.loadConfig(requireContext()) // Load the configuration
//        val imageUrl =
//            ConfigReader.getImageUrl() ?: "http://192.168.68.123:73/api/v1/mydoc/download/"
//        val imageUrl = "http://192.168.68.123:73/api/v1/mydoc/download/"+imageId
        var imageUrl =
            "https://s3.amazonaws.com/coursera_assets/meta_images/generated/CERTIFICATE_LANDING_PAGE/CERTIFICATE_LANDING_PAGE~TA9FQG4V38ZN/CERTIFICATE_LANDING_PAGE~TA9FQG4V38ZN.jpeg"

        ivFullImg.load(imageUrl) {
            size(coil.size.Size.ORIGINAL) // Load the actual size of the image
            placeholder(R.drawable.ic_upload_placeholder) // Shown while loading
            error(R.drawable.ic_alpha_x_circle)       // Shown if loading fails
        }

        // Handle button clicks
        ivFullImg.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        ivClose.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        // Show the dialog
        dialog.show()
    }
}
