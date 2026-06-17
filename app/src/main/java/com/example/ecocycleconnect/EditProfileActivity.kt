package com.example.ecocycleconnect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        val etFullName = findViewById<EditText>(R.id.et_full_name)
        val etPhoneNumber = findViewById<EditText>(R.id.et_phone_number)

        // Load existing data
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        etFullName.setText(sharedPref.getString("name", "Hashini Samuel"))
        etPhoneNumber.setText(sharedPref.getString("mobile", "+94 71 345 2365"))

        // Find the header to apply window insets
        val header = findViewById<LinearLayout>(R.id.header)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        // Fix: Changed ImageView to LinearLayout to match XML
        val btnBack = findViewById<LinearLayout>(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnCancel = findViewById<MaterialButton>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            finish()
        }

        val btnSave = findViewById<MaterialButton>(R.id.btn_save)
        btnSave.setOnClickListener {
            // Save updated details
            with(sharedPref.edit()) {
                putString("name", etFullName.text.toString())
                putString("mobile", etPhoneNumber.text.toString())
                apply()
            }

            // Start the success activity
            val intent = Intent(this, EditProfileSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
