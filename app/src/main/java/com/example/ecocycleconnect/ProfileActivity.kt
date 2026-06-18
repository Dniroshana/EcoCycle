package com.example.ecocycleconnect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Load and display user details from SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", "Hashini Samuel")
        
        val tvProfileName = findViewById<TextView>(R.id.profile_name_text)
        tvProfileName.text = name

        // Back Button
        val btnBack = findViewById<LinearLayout>(R.id.btn_back)
        btnBack?.setOnClickListener {
            finish()
        }

        // Settings Button
        val btnSettings = findViewById<LinearLayout>(R.id.btn_settings)
        btnSettings?.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Edit Profile Button - Navigate to EditProfileActivity
        val btnEditProfile = findViewById<LinearLayout>(R.id.btn_edit_profile)
        btnEditProfile?.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Collection History Button - Navigate to ActivityHistoryActivity
        val btnCollectionHistory = findViewById<LinearLayout>(R.id.btn_collection_history)
        btnCollectionHistory?.setOnClickListener {
            val intent = Intent(this, ActivityHistoryActivity::class.java)
            startActivity(intent)
        }

        // Delete Account Button - Navigate to DeleteAccountActivity
        val btnDeleteAccount = findViewById<LinearLayout>(R.id.btn_delete_account)
        btnDeleteAccount?.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }

        // Go Premium CTA
        val btnGoPremium = findViewById<LinearLayout>(R.id.button___go_premium_cta)
        btnGoPremium?.setOnClickListener {
            val intent = Intent(this, PremiumActivity::class.java)
            startActivity(intent)
        }

        // Handle Window Insets
        val topHeader = findViewById<android.view.View>(R.id.top_header)
        if (topHeader != null) {
            val initialPaddingTop = topHeader.paddingTop
            ViewCompat.setOnApplyWindowInsetsListener(topHeader) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(
                    left = systemBars.left,
                    top = systemBars.top + initialPaddingTop,
                    right = systemBars.right,
                    bottom = topHeader.paddingBottom
                )
                insets
            }
        }

        // Navigation from Bottom Nav
        val btnHome = findViewById<LinearLayout>(R.id.btn_home)
        btnHome?.setOnClickListener {
            finish()
        }

        val btnReport = findViewById<LinearLayout>(R.id.btn_report)
        btnReport?.setOnClickListener {
            val intent = Intent(this, ReportWasteActivity::class.java)
            startActivity(intent)
        }

        val btnActivity = findViewById<LinearLayout>(R.id.btn_activity)
        btnActivity?.setOnClickListener {
            val intent = Intent(this, ActivityHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
