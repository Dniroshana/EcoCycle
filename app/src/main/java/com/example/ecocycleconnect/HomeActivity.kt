package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Status bar setup
        val header = findViewById<View>(R.id.header___topappbar)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        // Set profile image
        val profileImageTop = findViewById<ImageView>(R.id.profile_image_top)
        // Using boy1.png as the user's profile image
        profileImageTop?.setImageResource(R.drawable.boy1)
        profileImageTop?.clipToOutline = true

        // NAVIGATION TO REPORT WASTE ACTIVITY
        // 1. Bottom Navigation Map Button
        findViewById<View>(R.id.btn_report)?.setOnClickListener {
            startActivity(Intent(this, ReportWasteActivity::class.java))
        }

        // Activity History Navigation
        findViewById<View>(R.id.btn_activity)?.setOnClickListener {
            startActivity(Intent(this, ActivityHistoryActivity::class.java))
        }

        // Schedule Pickup Navigation
        findViewById<View>(R.id.btn_schedule_pickup)?.setOnClickListener {
            startActivity(Intent(this, SchedulePickupActivity::class.java))
        }

        // OTHER NAVIGATIONS
        findViewById<View>(R.id.notification_icon)?.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        findViewById<View>(R.id.btn_profile_nav)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<View>(R.id.btn_select_vehicle)?.setOnClickListener {
            startActivity(Intent(this, SelectVehicleActivity::class.java))
        }

        findViewById<View>(R.id.btn_rewards)?.setOnClickListener {
            startActivity(Intent(this, RewardsActivity::class.java))
        }

        // Eco-Diary Navigation
        findViewById<View>(R.id.btn_eco_diary)?.setOnClickListener {
            startActivity(Intent(this, EcoDiaryActivity::class.java))
        }
        
        // Profile image click navigation
        profileImageTop?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
