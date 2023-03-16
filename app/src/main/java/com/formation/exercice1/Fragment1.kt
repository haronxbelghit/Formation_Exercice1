package com.formation.exercice1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class Fragment1 : Fragment() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var jobEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var telEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var profileImageButton: ImageButton
    private var profileImageUri: Uri? = null
    private lateinit var submitButton: Button

    private val sharedViewModel: FormViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        // Initialize Edit Texts
        firstNameEditText = view.findViewById(R.id.et_first_name)
        lastNameEditText = view.findViewById(R.id.et_last_name)
        cityEditText = view.findViewById(R.id.et_city)
        countryEditText = view.findViewById(R.id.et_country)
        jobEditText = view.findViewById(R.id.et_job)
        descEditText = view.findViewById(R.id.et_desc)
        phoneEditText = view.findViewById(R.id.et_phone)
        telEditText = view.findViewById(R.id.et_tel)
        emailEditText = view.findViewById(R.id.et_email)
        profileImageButton = view.findViewById(R.id.et_photo)
        submitButton = view.findViewById(R.id.btn_submit)


        // Set up the click listener for the Add Profile Image button
        profileImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        // Set up the click listener for the Submit button
        submitButton.setOnClickListener {

            // Get the values from the EditTexts
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val job = jobEditText.text.toString()
            val country = countryEditText.text.toString()
            val city = cityEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()
            val desc = descEditText.text.toString()


            profileImageButton.setOnClickListener {
                val galleryIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 1)

            }


            sharedViewModel.setFormData(
                FormData(
                    firstName,
                    lastName,
                    city,
                    country,
                    phone,
                    job,
                    desc,
                    email,
                    profileImageUri
                )
            )

            // Navigate to the Fragment2
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Fragment2())
                .addToBackStack(null)
                .commit()
        }



        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            profileImageUri = data.data
        }
    }

    override fun onResume() {
        super.onResume()

        // Enable the back button on the Toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = "Formulaire"
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
    }
}
