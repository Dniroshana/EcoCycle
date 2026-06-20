package com.example.ecocycleconnect

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class ReportSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_report_success)

        findViewById<View>(R.id.top_header)?.let { header ->
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        val ivReportedWaste = findViewById<ImageView>(R.id.iv_reported_waste)

        // Retrieve the image data from intent
        val imageUriString = intent.getStringExtra("image_uri")
        val imageByteArray = intent.getByteArrayExtra("image_bitmap")

        if (imageUriString != null) {
            ivReportedWaste.setImageURI(Uri.parse(imageUriString))
        } else if (imageByteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            ivReportedWaste.setImageBitmap(bitmap)
        }

        findViewById<MaterialButton>(R.id.btn_report_another).setOnClickListener {
            val intent = Intent(this, ReportWasteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        findViewById<MaterialButton>(R.id.btn_back_home).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
