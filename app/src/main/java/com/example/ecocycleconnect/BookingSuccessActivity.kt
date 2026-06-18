package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookingSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_success)

        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        displaySuccessDetails()

        findViewById<View>(R.id.btn_back_success)?.setOnClickListener {
            navigateToHome()
        }

        findViewById<View>(R.id.btn_back_to_home)?.setOnClickListener {
            navigateToHome()
        }

        findViewById<View>(R.id.btn_view_activity)?.setOnClickListener {
            val intent = Intent(this, ActivityHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displaySuccessDetails() {
        val date = intent.getStringExtra("SELECTED_DATE")
        val time = intent.getStringExtra("SELECTED_TIME")
        val vehicle = intent.getStringExtra("SELECTED_VEHICLE")
        
        // Mocking some data for the success screen as requested by the UI design
        findViewById<TextView>(R.id.tv_success_booking_id).text = "#EC-${(10000..99999).random()}"
        findViewById<TextView>(R.id.tv_success_date_time).text = "$date @ $time"
        
        val tvVehicleName = findViewById<TextView>(R.id.tv_success_vehicle_name)
        val ivVehicle = findViewById<ImageView>(R.id.iv_success_vehicle)

        when (vehicle) {
            "small" -> {
                tvVehicleName.text = "Small\nPickup"
                ivVehicle.setImageResource(R.drawable.small_pickup2)
            }
            "standard" -> {
                tvVehicleName.text = "Standard\nTruck"
                ivVehicle.setImageResource(R.drawable.standard_truck2)
            }
            "heavy" -> {
                tvVehicleName.text = "Heavy Duty\nTruck"
                ivVehicle.setImageResource(R.drawable.heavy_duty_truck)
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateToHome()
    }
}
