package com.formation.exercice1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class Fragment2 : Fragment() {

    private val PERMISSION_CALL = 0

    private lateinit var tel: TextView
    private lateinit var name: TextView
    private lateinit var job: TextView
    private lateinit var country: TextView
    private lateinit var desc: TextView
    private lateinit var city: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView
    private lateinit var photo: ImageView
    private val sharedViewModel: FormViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tel = view.findViewById(R.id.tv_res_tel)
        name = view.findViewById(R.id.tv_res_name)
        job = view.findViewById(R.id.tv_res_job)
        country = view.findViewById(R.id.tv_res_country)
        desc = view.findViewById(R.id.tv_res_desc)
        city = view.findViewById(R.id.tv_res_city)
        phone = view.findViewById(R.id.tv_res_phone)
        email = view.findViewById(R.id.tv_res_email)
        photo = view.findViewById(R.id.imageView)


        sharedViewModel.formData.observe(viewLifecycleOwner) {
            it?.let { contact -> updateContactDetails(contact) }
        }

//        // get text from interface and send to textView present in Fragment2
//        name.text = "${arguments?.getString("firstName")} ${arguments?.getString("lastName")}"
//        city.text = arguments?.getString("city")
//        country.text = arguments?.getString("country")
//        phone.text = arguments?.getString("phone")
//        job.text = arguments?.getString("job")
//        desc.text = arguments?.getString("desc")
//        email.text = arguments?.getString("tel")
//        tel.text = arguments?.getString("tel")
//
//        arguments?.getString("profileImageUri")?.toUri()?.let {
//            photo.setImageURI(it)
//        }

        view.findViewById<TextView>(R.id.tv_res_phone).setOnClickListener {
            if (checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_CALL
                )
            } else {
                makeCall(tel.text.toString())
            }
        }
        email.setOnClickListener {
            val resolveIntent = Intent(Intent.ACTION_SENDTO)
            resolveIntent.setData(Uri.parse("mailto:default@recipient.com"))
            val resolveInfoList =
                context?.packageManager?.queryIntentActivities(resolveIntent, PackageManager.MATCH_DEFAULT_ONLY)
            val intents = resolveInfoList?.mapNotNull { info -> context?.packageManager?.getLaunchIntentForPackage(info.activityInfo.packageName) }
                ?.toMutableList()
            if(intents?.isEmpty() == true) {
                //no mail client installed. Prompt user or throw exception
            } else if (intents != null) {
                if(intents.size == 1) {
                    //one mail client installed, start that
                    startActivity(intents.first())
                } else {
                    //multiple mail clients installed, let user choose which one to start
                    val chooser = Intent(Intent.ACTION_CHOOSER)
                    chooser.putExtra(Intent.EXTRA_INTENT, intents.removeAt(0))
                    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(chooser)
                }
            }
        }
    }

    private fun updateContactDetails(contact: FormData) {
        name.text = sharedViewModel.formData.value?.firstName ?: ""
        city.text = sharedViewModel.formData.value?.city ?: ""
        country.text = sharedViewModel.formData.value?.country ?: ""
        phone.text = sharedViewModel.formData.value?.phone ?: ""
        job.text = sharedViewModel.formData.value?.job ?: ""
        desc.text = sharedViewModel.formData.value?.desc ?: ""
        email.text = sharedViewModel.formData.value?.email ?: ""
        tel.text = sharedViewModel.formData.value?.phone ?: ""
        photo.setImageURI(Uri.parse((sharedViewModel.formData.value?.profileImageUri ?: "").toString()))
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CALL -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall(tel.text.toString())
                }
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_CALL
                )
            }
        }
    }

    private fun makeCall(num: String) {
        val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${num}"))
        startActivity(callIntent)
    }

    override fun onResume() {
        super.onResume()

        // Enable the back button on the Toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = (sharedViewModel.formData.value?.firstName
                ?: "") + " " + (sharedViewModel.formData.value?.lastName ?: "")
        }
    }

}