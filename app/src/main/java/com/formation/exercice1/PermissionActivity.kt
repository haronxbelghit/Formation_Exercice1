package com.formation.exercice1

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PermissionActivity : AppCompatActivity() {

    val PERMISSION_CALL = 0
    val PERMISSION_RECORD = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.context_layout)

        findViewById<Button>(R.id.make_call).setOnClickListener {

            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_CALL
                )
            } else
                makeCall()
        }

        findViewById<Button>(R.id.record).setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_CALL_LOG),
                    PERMISSION_RECORD
                )

            } else {
//                record()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CALL -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall()
                }
            }
//            PERMISSION_RECORD ->{
//                if (grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    record()
//                }
//            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE), PERMISSION_CALL
                )
            }
        }


    }


    private fun makeCall() {
        val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:555"))
        startActivity(callIntent)
    }
}