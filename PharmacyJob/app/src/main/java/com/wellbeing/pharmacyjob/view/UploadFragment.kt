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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wellbeing.pharmacyjob.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private lateinit var idImageView: ImageView
    private lateinit var certImageView: ImageView
    private lateinit var licenseImageView: ImageView

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

        binding.uploadIdButton.setOnClickListener {
            openGalleryForResult(REQUEST_ID)
        }

        binding.uploadCertButton.setOnClickListener {
            openGalleryForResult(REQUEST_CERT)
        }

        binding.uploadLicenseButton.setOnClickListener {
            openGalleryForResult(REQUEST_LICENSE)
        }

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
