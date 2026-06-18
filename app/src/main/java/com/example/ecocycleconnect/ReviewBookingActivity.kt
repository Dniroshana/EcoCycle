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

class ReviewBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_review_booking)

        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        displayReviewDetails()

        findViewById<View>(R.id.btn_back)?.setOnClickListener {
            finish()
        }

        // Handle Confirm & Pay button click
        findViewById<View>(R.id.btn_confirm_pay)?.setOnClickListener {
            val intentToSuccess = Intent(this, BookingSuccessActivity::class.java)
            
            // Pass the details forward to the success screen
            intentToSuccess.putExtra("SELECTED_DATE", intent.getStringExtra("SELECTED_DATE"))
            intentToSuccess.putExtra("SELECTED_TIME", intent.getStringExtra("SELECTED_TIME"))
            intentToSuccess.putExtra("SELECTED_VEHICLE", intent.getStringExtra("SELECTED_VEHICLE"))
            intentToSuccess.putStringArrayListExtra("SELECTED_WASTE_TYPES", intent.getStringArrayListExtra("SELECTED_WASTE_TYPES"))
            intentToSuccess.putExtra("SELECTED_LOCATION", intent.getStringExtra("SELECTED_LOCATION"))
            
            startActivity(intentToSuccess)
        }
    }

    private fun displayReviewDetails() {
        // Get data from intent
        val selectedDate = intent.getStringExtra("SELECTED_DATE")
        val selectedTime = intent.getStringExtra("SELECTED_TIME")
        val selectedWasteTypes = intent.getStringArrayListExtra("SELECTED_WASTE_TYPES")
        val selectedLocation = intent.getStringExtra("SELECTED_LOCATION")
        val selectedVehicle = intent.getStringExtra("SELECTED_VEHICLE")

        // Find views
        val tvLocation = findViewById<TextView>(R.id.tv_review_location)
        val tvReviewDate = findViewById<TextView>(R.id.tv_review_date)
        val tvReviewTime = findViewById<TextView>(R.id.tv_review_time)
        val tvWasteType = findViewById<TextView>(R.id.tv_review_waste_type)

        // Vehicle Views
        val tvVehicleName = findViewById<TextView>(R.id.tv_review_vehicle_name)
        val tvVehicleCapacity = findViewById<TextView>(R.id.tv_review_vehicle_capacity)
        val ivVehicleImage = findViewById<ImageView>(R.id.iv_review_vehicle_image)
        val ivVehicleTypeIcon = findViewById<ImageView>(R.id.iv_vehicle_type_icon)

        // Set text values
        tvLocation?.text = selectedLocation ?: "No location selected"
        tvReviewDate?.text = selectedDate ?: "N/A"
        tvReviewTime?.text = selectedTime ?: "N/A"

        // Set Waste Details
        if (!selectedWasteTypes.isNullOrEmpty()) {
            val joinedTypes = selectedWasteTypes.joinToString(", ") { type ->
                type.replaceFirstChar { it.uppercase() }
            }
            tvWasteType?.text = "$joinedTypes Waste"
        } else {
            tvWasteType?.text = "No waste selected"
        }

        // Set Vehicle Details
        when (selectedVehicle) {
            "small" -> {
                tvVehicleName?.text = "Small Pickup"
                tvVehicleCapacity?.text = "Max Load: 500kg"
                ivVehicleImage?.setImageResource(R.drawable.small_pickup2)
                ivVehicleTypeIcon?.setImageResource(R.drawable.small_pickup2)
            }
            "standard" -> {
                tvVehicleName?.text = "Standard Truck"
                tvVehicleCapacity?.text = "Max Load: 2,000kg"
                ivVehicleImage?.setImageResource(R.drawable.standard_truck2)
                ivVehicleTypeIcon?.setImageResource(R.drawable.small_truck)
            }
            "heavy" -> {
                tvVehicleName?.text = "Heavy Duty Truck"
                tvVehicleCapacity?.text = "Max Load: 8,000kg"
                ivVehicleImage?.setImageResource(R.drawable.heavy_duty_truck)
                ivVehicleTypeIcon?.setImageResource(R.drawable.heavy_duty_truck)
            }
            else -> {
                tvVehicleName?.text = "Standard Truck"
                tvVehicleCapacity?.text = "Max Load: 2,000kg"
                ivVehicleImage?.setImageResource(R.drawable.standard_truck2)
            }
        }
    }
}
