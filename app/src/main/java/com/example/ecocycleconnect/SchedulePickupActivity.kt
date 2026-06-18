package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SchedulePickupActivity : AppCompatActivity() {

    // Selection Tracking
    private var selectedDate: String? = null
    private var selectedTimeSlot: String? = null
    private var selectedTimeRange: String? = null
    private var selectedWasteTypes: MutableSet<String> = mutableSetOf()
    private var selectedVehicle: String? = null

    // Date
    private lateinit var calendarView: CalendarView

    // Time Slots
    private lateinit var layoutMorning: View
    private lateinit var layoutMidday: View
    private lateinit var layoutAfternoon: View
    private lateinit var checkMorning: ImageView
    private lateinit var checkMidday: ImageView
    private lateinit var checkAfternoon: ImageView
    private lateinit var tvMorningTitle: TextView
    private lateinit var tvMorningTime: TextView
    private lateinit var iconMorning: ImageView
    private lateinit var tvMiddayTitle: TextView
    private lateinit var tvMiddayTime: TextView
    private lateinit var iconMidday: ImageView
    private lateinit var tvAfternoonTitle: TextView
    private lateinit var tvAfternoonTime: TextView
    private lateinit var iconAfternoon: ImageView

    // Waste Types
    private lateinit var layoutPlastic: View
    private lateinit var layoutOrganic: View
    private lateinit var layoutPaper: View
    private lateinit var layoutGlass: View
    private lateinit var layoutElectronic: View
    private lateinit var layoutOther: View
    private lateinit var iconPlastic: ImageView
    private lateinit var iconOrganic: ImageView
    private lateinit var iconPaper: ImageView
    private lateinit var iconGlass: ImageView
    private lateinit var iconElectronic: ImageView
    private lateinit var iconOther: ImageView
    private lateinit var tvPlastic: TextView
    private lateinit var tvOrganic: TextView
    private lateinit var tvPaper: TextView
    private lateinit var tvGlass: TextView
    private lateinit var tvElectronic: TextView
    private lateinit var tvOther: TextView

    // Vehicles
    private lateinit var layoutVehicleSmall: View
    private lateinit var layoutVehicleStandard: View
    private lateinit var layoutVehicleHeavy: View
    private lateinit var checkVehicleSmall: ImageView
    private lateinit var checkVehicleStandard: ImageView
    private lateinit var checkVehicleHeavy: ImageView
    private lateinit var iconVehicleSmall: ImageView
    private lateinit var iconVehicleStandard: ImageView
    private lateinit var iconVehicleHeavy: ImageView
    private lateinit var tvVehicleSmallName: TextView
    private lateinit var tvVehicleStandardName: TextView
    private lateinit var tvVehicleHeavyName: TextView
    private lateinit var tvVehicleSmallCap: TextView
    private lateinit var tvVehicleStandardCap: TextView
    private lateinit var tvVehicleHeavyCap: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule_pickup)

        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupDateSelection()
        setupTimeSelection()
        setupWasteSelection()
        setupVehicleSelection()

        findViewById<View>(R.id.btn_back)?.setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.btn_confirm)?.setOnClickListener {
            validateAndProceed()
        }

        // Initially reset UI to match null selections (forcing user to choose)
        resetAllSelectionsUI()
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendar_view)

        // Time Slots
        layoutMorning = findViewById(R.id.layout_morning)
        layoutMidday = findViewById(R.id.layout_midday)
        layoutAfternoon = findViewById(R.id.layout_afternoon)
        checkMorning = findViewById(R.id.check_morning)
        checkMidday = findViewById(R.id.check_midday)
        checkAfternoon = findViewById(R.id.check_afternoon)
        tvMorningTitle = findViewById(R.id.tv_morning)
        tvMorningTime = findViewById(R.id.tv_morning_time)
        iconMorning = findViewById(R.id.icon_morning)
        tvMiddayTitle = findViewById(R.id.tv_midday)
        tvMiddayTime = findViewById(R.id.tv_midday_time)
        iconMidday = findViewById(R.id.icon_midday)
        tvAfternoonTitle = findViewById(R.id.tv_afternoon)
        tvAfternoonTime = findViewById(R.id.tv_afternoon_time)
        iconAfternoon = findViewById(R.id.icon_afternoon)

        // Waste Types
        layoutPlastic = findViewById(R.id.layout_plastic)
        layoutOrganic = findViewById(R.id.layout_organic)
        layoutPaper = findViewById(R.id.layout_paper)
        layoutGlass = findViewById(R.id.layout_glass)
        layoutElectronic = findViewById(R.id.layout_electronic)
        layoutOther = findViewById(R.id.layout_other)
        iconPlastic = findViewById(R.id.icon_plastic)
        iconOrganic = findViewById(R.id.icon_organic)
        iconPaper = findViewById(R.id.icon_paper)
        iconGlass = findViewById(R.id.icon_glass)
        iconElectronic = findViewById(R.id.icon_electronic)
        iconOther = findViewById(R.id.icon_other)
        tvPlastic = findViewById(R.id.tv_plastic)
        tvOrganic = findViewById(R.id.tv_organic)
        tvPaper = findViewById(R.id.tv_paper)
        tvGlass = findViewById(R.id.tv_glass)
        tvElectronic = findViewById(R.id.tv_electronic)
        tvOther = findViewById(R.id.tv_other)

        // Vehicles
        layoutVehicleSmall = findViewById(R.id.layout_vehicle_small)
        layoutVehicleStandard = findViewById(R.id.layout_vehicle_standard)
        layoutVehicleHeavy = findViewById(R.id.layout_vehicle_heavy)
        checkVehicleSmall = findViewById(R.id.check_vehicle_small)
        checkVehicleStandard = findViewById(R.id.check_vehicle_standard)
        checkVehicleHeavy = findViewById(R.id.check_vehicle_heavy)
        iconVehicleSmall = findViewById(R.id.iv_vehicle1)
        iconVehicleStandard = findViewById(R.id.iv_vehicle2)
        iconVehicleHeavy = findViewById(R.id.iv_vehicle3)
        tvVehicleSmallName = findViewById(R.id.tv_v1_name)
        tvVehicleStandardName = findViewById(R.id.tv_v2_name)
        tvVehicleHeavyName = findViewById(R.id.tv_v3_name)
        tvVehicleSmallCap = findViewById(R.id.tv_v1_capacity)
        tvVehicleStandardCap = findViewById(R.id.tv_v2_capacity)
        tvVehicleHeavyCap = findViewById(R.id.tv_v3_capacity)
    }

    private fun setupDateSelection() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }
    }

    private fun setupTimeSelection() {
        layoutMorning.setOnClickListener { selectTimeSlot("morning", "08:00 AM - 10:00 AM") }
        layoutMidday.setOnClickListener { selectTimeSlot("midday", "10:00 AM - 12:00 PM") }
        layoutAfternoon.setOnClickListener { selectTimeSlot("afternoon", "02:00 PM - 04:00 PM") }
    }

    private fun setupWasteSelection() {
        layoutPlastic.setOnClickListener { toggleWasteType("plastic") }
        layoutOrganic.setOnClickListener { toggleWasteType("organic") }
        layoutPaper.setOnClickListener { toggleWasteType("paper") }
        layoutGlass.setOnClickListener { toggleWasteType("glass") }
        layoutElectronic.setOnClickListener { toggleWasteType("electronic") }
        layoutOther.setOnClickListener { toggleWasteType("other") }
    }

    private fun setupVehicleSelection() {
        layoutVehicleSmall.setOnClickListener { selectVehicle("small") }
        layoutVehicleStandard.setOnClickListener { selectVehicle("standard") }
        layoutVehicleHeavy.setOnClickListener { selectVehicle("heavy") }
    }

    private fun resetAllSelectionsUI() {
        // Reset Time Slots
        resetTimeSlot(layoutMorning, tvMorningTitle, tvMorningTime, iconMorning, checkMorning)
        resetTimeSlot(layoutMidday, tvMiddayTitle, tvMiddayTime, iconMidday, checkMidday)
        resetTimeSlot(layoutAfternoon, tvAfternoonTitle, tvAfternoonTime, iconAfternoon, checkAfternoon)

        // Reset Vehicles
        resetVehicleSlot(layoutVehicleSmall, iconVehicleSmall, checkVehicleSmall, tvVehicleSmallName, tvVehicleSmallCap)
        resetVehicleSlot(layoutVehicleStandard, iconVehicleStandard, checkVehicleStandard, tvVehicleStandardName, tvVehicleStandardCap)
        resetVehicleSlot(layoutVehicleHeavy, iconVehicleHeavy, checkVehicleHeavy, tvVehicleHeavyName, tvVehicleHeavyCap)
    }

    private fun validateAndProceed() {
        if (selectedDate == null) {
            Toast.makeText(this, "Please select a date from the calendar", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedTimeSlot == null) {
            Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedWasteTypes.isEmpty()) {
            Toast.makeText(this, "Please select at least one type of waste", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedVehicle == null) {
            Toast.makeText(this, "Please select a vehicle", Toast.LENGTH_SHORT).show()
            return
        }

        // All valid, proceed to summary
        val intent = Intent(this, BookingSummaryActivity::class.java).apply {
            putExtra("SELECTED_DATE", selectedDate)
            putExtra("SELECTED_TIME", selectedTimeRange)
            putExtra("SELECTED_VEHICLE", selectedVehicle)
            // Pass waste types as an ArrayList
            putStringArrayListExtra("SELECTED_WASTE_TYPES", ArrayList(selectedWasteTypes.toList()))
        }
        startActivity(intent)
    }

    private fun selectTimeSlot(slot: String, range: String) {
        selectedTimeSlot = slot
        selectedTimeRange = range
        resetTimeSlot(layoutMorning, tvMorningTitle, tvMorningTime, iconMorning, checkMorning)
        resetTimeSlot(layoutMidday, tvMiddayTitle, tvMiddayTime, iconMidday, checkMidday)
        resetTimeSlot(layoutAfternoon, tvAfternoonTitle, tvAfternoonTime, iconAfternoon, checkAfternoon)

        when (slot) {
            "morning" -> setTimeSlotSelected(layoutMorning, tvMorningTitle, tvMorningTime, iconMorning, checkMorning)
            "midday" -> setTimeSlotSelected(layoutMidday, tvMiddayTitle, tvMiddayTime, iconMidday, checkMidday)
            "afternoon" -> setTimeSlotSelected(layoutAfternoon, tvAfternoonTitle, tvAfternoonTime, iconAfternoon, checkAfternoon)
        }
    }

    private fun toggleWasteType(type: String) {
        val isSelected = selectedWasteTypes.contains(type)
        if (isSelected) {
            selectedWasteTypes.remove(type)
        } else {
            selectedWasteTypes.add(type)
        }

        when (type) {
            "plastic" -> updateWasteSlotUI(layoutPlastic, iconPlastic, tvPlastic, !isSelected)
            "organic" -> updateWasteSlotUI(layoutOrganic, iconOrganic, tvOrganic, !isSelected)
            "paper" -> updateWasteSlotUI(layoutPaper, iconPaper, tvPaper, !isSelected)
            "glass" -> updateWasteSlotUI(layoutGlass, iconGlass, tvGlass, !isSelected)
            "electronic" -> updateWasteSlotUI(layoutElectronic, iconElectronic, tvElectronic, !isSelected)
            "other" -> updateWasteSlotUI(layoutOther, iconOther, tvOther, !isSelected)
        }
    }

    private fun selectVehicle(type: String) {
        selectedVehicle = type
        resetVehicleSlot(layoutVehicleSmall, iconVehicleSmall, checkVehicleSmall, tvVehicleSmallName, tvVehicleSmallCap)
        resetVehicleSlot(layoutVehicleStandard, iconVehicleStandard, checkVehicleStandard, tvVehicleStandardName, tvVehicleStandardCap)
        resetVehicleSlot(layoutVehicleHeavy, iconVehicleHeavy, checkVehicleHeavy, tvVehicleHeavyName, tvVehicleHeavyCap)

        when (type) {
            "small" -> setVehicleSlotSelected(layoutVehicleSmall, iconVehicleSmall, checkVehicleSmall, tvVehicleSmallName, tvVehicleSmallCap)
            "standard" -> setVehicleSlotSelected(layoutVehicleStandard, iconVehicleStandard, checkVehicleStandard, tvVehicleStandardName, tvVehicleStandardCap)
            "heavy" -> setVehicleSlotSelected(layoutVehicleHeavy, iconVehicleHeavy, checkVehicleHeavy, tvVehicleHeavyName, tvVehicleHeavyCap)
        }
    }

    private fun resetTimeSlot(layout: View, title: TextView, time: TextView, icon: ImageView, check: ImageView) {
        layout.setBackgroundResource(R.drawable.bg_rounded)
        title.setTextColor(ContextCompat.getColor(this, R.color.text_black))
        time.setTextColor(ContextCompat.getColor(this, R.color.text_light_grey))
        icon.setColorFilter(ContextCompat.getColor(this, R.color.text_light_grey))
        check.visibility = View.GONE
    }

    private fun setTimeSlotSelected(layout: View, title: TextView, time: TextView, icon: ImageView, check: ImageView) {
        layout.setBackgroundResource(R.drawable.bg_selected_item)
        title.setTextColor(ContextCompat.getColor(this, R.color.white))
        time.setTextColor(ContextCompat.getColor(this, R.color.white))
        icon.setColorFilter(ContextCompat.getColor(this, R.color.white))
        check.visibility = View.VISIBLE
    }

    private fun updateWasteSlotUI(layout: View, icon: ImageView, text: TextView, isSelected: Boolean) {
        if (isSelected) {
            layout.setBackgroundResource(R.drawable.bg_selected_item)
            icon.setColorFilter(ContextCompat.getColor(this, R.color.white))
            text.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            layout.setBackgroundResource(R.drawable.bg_rounded)
            icon.setColorFilter(null)
            text.setTextColor(ContextCompat.getColor(this, R.color.text_black))
        }
    }

    private fun resetVehicleSlot(layout: View, icon: ImageView, check: ImageView, name: TextView, capacity: TextView) {
        layout.setBackgroundResource(R.drawable.bg_rounded)
        icon.setColorFilter(null)
        check.visibility = View.GONE
        name.setTextColor(ContextCompat.getColor(this, R.color.text_black))
        capacity.setTextColor(ContextCompat.getColor(this, R.color.text_light_grey))
    }

    private fun setVehicleSlotSelected(layout: View, icon: ImageView, check: ImageView, name: TextView, capacity: TextView) {
        layout.setBackgroundResource(R.drawable.bg_selected_item)
        icon.setColorFilter(null)
        check.visibility = View.VISIBLE
        name.setTextColor(ContextCompat.getColor(this, R.color.white))
        capacity.setTextColor(ContextCompat.getColor(this, R.color.white))
    }
}
