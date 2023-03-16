package com.formation.exercice1

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val PERMISSION_CALL = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create Fragment1
        val fragment1 = Fragment1()

        // Frame manager to place Fragment1
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment1)
            .commit()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }

        if (!packageManager.hasSystemFeature("PERMISSION_CALL")) {
            requestPermissions(
                arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_CALL
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_CALL
                )
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Navigate back to the previous fragment
                supportFragmentManager.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Override function which we
    // have created in our interface
//    override fun passData(
//        firstName: String,
//        lastName: String,
//        city: String,
//        country: String,
//        phone: String,
//        job: String,
//        desc: String,
//        email: String,
//        profileImageUri: String?
//    ) {
//        val bundle = Bundle().apply {
//            putString("firstName", firstName)
//            putString("lastName", lastName)
//            putString("city", city)
//            putString("country", country)
//            putString("phone", phone)
//            putString("job", job)
//            putString("desc", desc)
//            putString("email", email)
//            putString("profileImageUri", profileImageUri)
//        }
//
//        val transaction = supportFragmentManager.beginTransaction()
//
//        // Created instance of fragment2
//        val fragment2 = Fragment2()
//
//
//        fragment2.arguments = bundle
//        transaction.replace(R.id.fragment_container_view, fragment2)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
}