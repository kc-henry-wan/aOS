package com.wellbeing.pharmacyjob.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.databinding.FragmentProfileBinding
import com.wellbeing.pharmacyjob.factory.UploadDocViewModelFactory
import com.wellbeing.pharmacyjob.factory.UserDetailViewModelFactory
import com.wellbeing.pharmacyjob.factory.UserUpdateViewModelFactory
import com.wellbeing.pharmacyjob.model.UserUpdateRequest
import com.wellbeing.pharmacyjob.repository.UploadDocRepository
import com.wellbeing.pharmacyjob.repository.UserDetailRepository
import com.wellbeing.pharmacyjob.repository.UserUpdateRepository
import com.wellbeing.pharmacyjob.utils.UserDataValidator.validateUserRequest
import com.wellbeing.pharmacyjob.viewmodel.UploadDocViewModel
import com.wellbeing.pharmacyjob.viewmodel.UserDetailViewModel
import com.wellbeing.pharmacyjob.viewmodel.UserUpdateViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvEmail: TextView
    private lateinit var dataFirstname: EditText
    private lateinit var dataLastName: EditText
    private lateinit var dataMobile: EditText
    private lateinit var dataAddress1: EditText
    private lateinit var dataAddress2: EditText
    private lateinit var dataPostalcode: EditText

    private lateinit var backButton: Button
    private lateinit var editButton: Button
    private lateinit var saveButton: Button
    private lateinit var uploadButton: Button
    private lateinit var profileImageView: ImageView

    private lateinit var apiResultTextView: TextView
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var userUpdateViewModel: UserUpdateViewModel
    private lateinit var uploadDocViewModel: UploadDocViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppLogger.d("ProfileFragment", "ProfileFragment --- onViewCreated")

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvEmail = binding.dataEmail
        dataFirstname = binding.dataFirstname
        dataLastName = binding.dataLastName
        dataMobile = binding.dataMobile
        dataAddress1 = binding.dataAddress1
        dataAddress2 = binding.dataAddress2
        dataPostalcode = binding.dataPostalcode
//        backButton = binding.btnBack
        saveButton = binding.btnSave
        uploadButton = binding.btnUpload
        editButton = binding.btnEdit
        profileImageView = binding.profileImageView

        apiResultTextView = binding.apiResultTextView

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = UserDetailRepository(apiService)
        val repositoryUpdate = UserUpdateRepository(apiService)
        val repositoryUpload = UploadDocRepository(apiService)
        var updatedAt: String = ""

        userDetailViewModel = ViewModelProvider(this, UserDetailViewModelFactory(repository))
            .get(UserDetailViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        userDetailViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    val user = result.data?.data

                    tvEmail.text = user?.email
                    dataMobile.setText(user?.mobile)
                    dataFirstname.setText(user?.firstName)
                    dataLastName.setText(user?.lastName)
                    dataAddress1.setText(user?.address1)
                    dataAddress2.setText(user?.address2 ?: "")
                    dataPostalcode.setText(user?.postalCode)
                    updatedAt = user?.updatedAt.toString()
                }

                is ApiResult.Error -> {
                    AppLogger.d("ProfileFragment", "API fail")
                    apiResultTextView.text = getString(R.string.api_get_fail)
                }
            }
        })

        userUpdateViewModel = ViewModelProvider(this, UserUpdateViewModelFactory(repositoryUpdate))
            .get(UserUpdateViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        userUpdateViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
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


        uploadDocViewModel =
            ViewModelProvider(this, UploadDocViewModelFactory(repositoryUpload))
                .get(UploadDocViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        uploadDocViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    apiResultTextView.text = getString(R.string.api_update_success)
                }

                is ApiResult.Error -> {
                    apiResultTextView.text = getString(R.string.api_update_fail)
                }
            }
        })

//        backButton.setOnClickListener {
//            onBackButton()
//        }
        saveButton.setOnClickListener {
            val request = UserUpdateRequest(
                "8", dataFirstname.text.toString(),
                dataLastName.text.toString(),
                dataMobile.text.toString(),
                dataAddress1.text.toString(),
                dataAddress2.text.toString(),
                dataPostalcode.text.toString(), updatedAt
            )
            val errors = validateUserRequest(request)
            if (errors.size == 0) {
                userUpdateViewModel.updateUserDetail(request)
            } else {
                apiResultTextView.text = errors.joinToString(separator = "\n")
                apiResultTextView.setTextColor(Color.RED)
            }
        }
        editButton.setOnClickListener {
            saveButton.visibility = View.VISIBLE
            editButton.visibility = View.GONE
            dataFirstname.isFocusableInTouchMode = true
            dataLastName.isFocusableInTouchMode = true
            dataMobile.isFocusableInTouchMode = true
            dataAddress1.isFocusableInTouchMode = true
            dataAddress2.isFocusableInTouchMode = true
            dataPostalcode.isFocusableInTouchMode = true
        }
        uploadButton.setOnClickListener {
//            showImagePickerOptions()

            val uploadFragment = UploadFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, uploadFragment)
                .addToBackStack(null)
                .commit()
        }

        userDetailViewModel.getUserDetail()
        saveButton.visibility = View.GONE

        // Initially, make the EditText read-only
        dataFirstname.isFocusableInTouchMode = false
        dataLastName.isFocusableInTouchMode = false
        dataMobile.isFocusableInTouchMode = false
        dataAddress1.isFocusableInTouchMode = false
        dataAddress2.isFocusableInTouchMode = false
        dataPostalcode.isFocusableInTouchMode = false
    }

    private fun onBackButton() {
//            findNavController().navigateUp()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun showImagePickerOptions() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Select Option")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA), 100
            )
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    // Handle camera result
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photo = result.data?.extras?.get("data") as? Bitmap
                profileImageView.setImageBitmap(photo)
            }
        }

    // Handle gallery result
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    profileImageView.setImageURI(imageUri)
                    uploadImageToServer(imageUri)
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        }
    }

    private fun uploadImageToServer(imageUri: Uri) {
        // Convert Uri to File
        val file = getFileFromUri(requireContext(), imageUri)

        if (file != null) {
            // Create RequestBody and MultipartBody.Part
            val requestFile = RequestBody.create("image/*".toMediaType(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            //Call API
            uploadDocViewModel.uploadDoc("Document", body)
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val tempFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "temp_image.jpg"
        )
        val outputStream = FileOutputStream(tempFile)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return tempFile
    }
}
