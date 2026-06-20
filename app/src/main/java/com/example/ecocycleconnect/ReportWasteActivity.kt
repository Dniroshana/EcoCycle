package com.example.ecocycleconnect

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class  ReportWasteActivity : AppCompatActivity() {

    private lateinit var ivSelectedPhoto: ImageView
    private lateinit var tvTakePhoto: TextView
    private lateinit var tvLocation: TextView
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var map: MapView
    private lateinit var cgWasteType: ChipGroup

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private var marker: Marker? = null
    private var selectedBitmap: Bitmap? = null
    private var selectedQuantityId: Int = -1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        enableEdgeToEdge()
        setContentView(R.layout.activity_report_waste)

        ivSelectedPhoto = findViewById(R.id.iv_selected_photo)
        tvTakePhoto = findViewById(R.id.tv_take_photo)
        tvLocation = findViewById(R.id.tv_location)
        nestedScrollView = findViewById(R.id.nested_scroll_view)
        map = findViewById(R.id.mapview)
        cgWasteType = findViewById(R.id.cg_waste_type)

        val header = findViewById<View>(R.id.header___top_appbar)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        setupLaunchers()
        setupMap()
        setupQuantitySelection()

        map.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                nestedScrollView.requestDisallowInterceptTouchEvent(true)
            }
            false
        }

        findViewById<View>(R.id.btn_photo_upload)?.setOnClickListener {
            showImageSourceDialog()
        }

        findViewById<View>(R.id.btn_select_location)?.setOnClickListener {
            nestedScrollView.smoothScrollTo(0, map.top - 100)
            Toast.makeText(this, "Tap on the map to select a location", Toast.LENGTH_SHORT).show()
        }

        findViewById<MaterialButton>(R.id.btn_submit).setOnClickListener {
            validateAndSubmit()
        }

        findViewById<View>(R.id.btn_back)?.setOnClickListener { finish() }

        // Bottom Navigation Listeners
        findViewById<View>(R.id.btn_home)?.setOnClickListener { finish() }

        findViewById<View>(R.id.btn_activity)?.setOnClickListener {
            val intent = Intent(this, ActivityHistoryActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_profile)?.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateAndSubmit() {
        if (selectedBitmap == null) {
            Toast.makeText(this, "Please add a photo of the waste", Toast.LENGTH_SHORT).show()
            return
        }
        if (marker == null) {
            Toast.makeText(this, "Please select a location on the map", Toast.LENGTH_SHORT).show()
            return
        }
        if (cgWasteType.checkedChipId == View.NO_ID) {
            Toast.makeText(this, "Please select the type of waste", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedQuantityId == -1) {
            Toast.makeText(this, "Please select the estimated quantity", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedChipId = cgWasteType.checkedChipId
        val chip = findViewById<Chip>(selectedChipId)
        val wasteType = chip.text.toString()
        val address = tvLocation.text.toString()
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())

        // Scaled bitmap for the list to avoid memory issues
        val scaledBitmap = Bitmap.createScaledBitmap(selectedBitmap!!, 200, 200, true)

        val newReport = WasteReport(
            type = wasteType,
            date = currentDate,
            location = address,
            points = 50,
            imageBitmap = scaledBitmap
        )
        ReportRepository.addReport(newReport)

        val intent = Intent(this, ReportSuccessActivity::class.java)
        val stream = ByteArrayOutputStream()
        selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 40, stream)
        val byteArray = stream.toByteArray()
        intent.putExtra("image_bitmap", byteArray)
        startActivity(intent)
        finish()
    }

    private fun setupQuantitySelection() {
        val qtyButtons = listOf(
            findViewById<LinearLayout>(R.id.btn_qty_small),
            findViewById<LinearLayout>(R.id.btn_qty_large),
            findViewById<LinearLayout>(R.id.btn_qty_multiple),
            findViewById<LinearLayout>(R.id.btn_qty_pile)
        )
        qtyButtons.forEach { button ->
            button?.setOnClickListener { clickedButton ->
                qtyButtons.forEach { it?.isSelected = false }
                clickedButton.isSelected = true
                selectedQuantityId = clickedButton.id
            }
        }
    }

    private fun setupMap() {
        map.setMultiTouchControls(true)
        val controller = map.controller
        controller.setZoom(15.0)
        val colombo = GeoPoint(6.9271, 79.8612)
        controller.setCenter(colombo)

        val eventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                p?.let {
                    updateMarker(it)
                    updateAddressText(it)
                }
                return true
            }
            override fun longPressHelper(p: GeoPoint?): Boolean = false
        }
        map.overlays.add(MapEventsOverlay(eventsReceiver))
    }

    private fun updateMarker(point: GeoPoint) {
        if (marker == null) {
            marker = Marker(map)
            marker?.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(marker)
        }
        marker?.position = point
        map.invalidate()
    }

    private fun updateAddressText(point: GeoPoint) {
        tvLocation.text = "Fetching address..."
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                tvLocation.text = addresses[0].getAddressLine(0)
            } else {
                tvLocation.text = "Lat: ${point.latitude}, Lng: ${point.longitude}"
            }
        } catch (e: Exception) {
            tvLocation.text = "Lat: ${point.latitude}, Lng: ${point.longitude}"
        }
    }

    private fun setupLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as? Bitmap
                bitmap?.let {
                    selectedBitmap = it
                    ivSelectedPhoto.setImageBitmap(it)
                    ivSelectedPhoto.imageTintList = null
                    tvTakePhoto.text = "Photo Captured"
                }
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let { uri ->
                    try {
                        val inputStream: InputStream? = contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        selectedBitmap = bitmap
                        ivSelectedPhoto.setImageBitmap(bitmap)
                        ivSelectedPhoto.imageTintList = null
                        tvTakePhoto.text = "Photo Selected"
                    } catch (e: Exception) {
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Image Source")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraLauncher.launch(intent)
                }
                1 -> {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    galleryLauncher.launch(intent)
                }
                2 -> dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onResume() { super.onResume(); map.onResume() }
    override fun onPause() { super.onPause(); map.onPause() }
}
