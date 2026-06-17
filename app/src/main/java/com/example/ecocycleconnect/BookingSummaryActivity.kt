package com.example.ecocycleconnect

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.Locale

class BookingSummaryActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var tvLocation: TextView
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Osmdroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_summary)

        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        initMap()
        displayBookingDetails()

        // Back button
        findViewById<View>(R.id.btn_back)?.setOnClickListener {
            finish()
        }

        // Edit schedule button
        findViewById<View>(R.id.btn_edit_schedule)?.setOnClickListener {
            finish()
        }

        // Proceed to confirmation button
        findViewById<View>(R.id.btn_proceed)?.setOnClickListener {
            val intentToReview = Intent(this, ReviewBookingActivity::class.java)
            
            // Pass all current details to the review screen
            intentToReview.putExtra("SELECTED_DATE", intent.getStringExtra("SELECTED_DATE"))
            intentToReview.putExtra("SELECTED_TIME", intent.getStringExtra("SELECTED_TIME"))
            intentToReview.putExtra("SELECTED_VEHICLE", intent.getStringExtra("SELECTED_VEHICLE"))
            intentToReview.putStringArrayListExtra("SELECTED_WASTE_TYPES", intent.getStringArrayListExtra("SELECTED_WASTE_TYPES"))
            intentToReview.putExtra("SELECTED_LOCATION", tvLocation.text.toString())
            
            startActivity(intentToReview)
        }
    }

    private fun initMap() {
        map = findViewById(R.id.mapview)
        tvLocation = findViewById(R.id.tv_location)
        
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(15.0)
        
        // Default to Colombo
        val startPoint = GeoPoint(6.9271, 79.8612)
        mapController.setCenter(startPoint)

        val mReceive: MapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                updateMarkerAndAddress(p)
                return true
            }
            override fun longPressHelper(p: GeoPoint): Boolean = false
        }

        val overlayEvents = MapEventsOverlay(mReceive)
        map.overlays.add(overlayEvents)
    }

    private fun updateMarkerAndAddress(point: GeoPoint) {
        if (marker != null) map.overlays.remove(marker)
        marker = Marker(map).apply {
            position = point
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = "Pickup Location"
        }
        map.overlays.add(marker)
        map.invalidate()

        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                tvLocation.text = addresses[0].getAddressLine(0)
            }
        } catch (e: Exception) {
            tvLocation.text = "Location selected"
        }
    }

    private fun displayBookingDetails() {
        val selectedDate = intent.getStringExtra("SELECTED_DATE")
        val selectedTime = intent.getStringExtra("SELECTED_TIME")
        val selectedWasteTypes = intent.getStringArrayListExtra("SELECTED_WASTE_TYPES")

        findViewById<TextView>(R.id.tv_summary_date).text = selectedDate ?: "Not Selected"
        findViewById<TextView>(R.id.tv_summary_time).text = selectedTime ?: "Not Selected"

        if (!selectedWasteTypes.isNullOrEmpty()) {
            val joinedTypes = selectedWasteTypes.joinToString(", ") { it.replaceFirstChar { c -> c.uppercase() } }
            findViewById<TextView>(R.id.tv_waste_type).text = "$joinedTypes Waste"

            val iconRes = when (selectedWasteTypes[0]) {
                "plastic" -> R.drawable.waterbottle
                "organic" -> R.drawable.compost
                "paper" -> R.drawable.paperstack
                "glass" -> R.drawable.glass34
                "electronic" -> R.drawable.ewaste
                else -> R.drawable.garbage234
            }
            findViewById<ImageView>(R.id.iv_waste_icon).setImageResource(iconRes)
        }
    }

    override fun onResume() { super.onResume(); map.onResume() }
    override fun onPause() { super.onPause(); map.onPause() }
}
