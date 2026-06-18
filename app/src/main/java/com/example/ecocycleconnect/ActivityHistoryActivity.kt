package com.example.ecocycleconnect

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activity_history)

        val header = findViewById<View>(R.id.top_header)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        findViewById<View>(R.id.btn_back)?.setOnClickListener { finish() }
        findViewById<View>(R.id.btn_home)?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        findViewById<View>(R.id.btn_map)?.setOnClickListener {
            startActivity(Intent(this, ReportWasteActivity::class.java))
        }
        findViewById<View>(R.id.btn_profile)?.setOnClickListener {
            // Check if ProfileActivity exists, if not fallback to Home
            try {
                startActivity(Intent(this, ProfileActivity::class.java))
            } catch (e: Exception) {
                finish()
            }
        }

        displayReports()
    }

    private fun displayReports() {
        val container = findViewById<LinearLayout>(R.id.entries_container)
        val emptyState = findViewById<TextView>(R.id.tv_empty_state)
        val reports = ReportRepository.reports

        container.removeAllViews()

        if (reports.isEmpty()) {
            emptyState.visibility = View.VISIBLE
        } else {
            emptyState.visibility = View.GONE
            val inflater = LayoutInflater.from(this)

            reports.forEach { report ->
                val itemView = inflater.inflate(R.layout.item_activity_entry, container, false)

                val tvType = itemView.findViewById<TextView>(R.id.tv_waste_type)
                val tvDateLoc = itemView.findViewById<TextView>(R.id.tv_date_location)
                val tvPoints = itemView.findViewById<TextView>(R.id.tv_points)
                val ivPhoto = itemView.findViewById<ImageView>(R.id.iv_entry_photo)
                val ivIcon = itemView.findViewById<ImageView>(R.id.iv_type_icon)

                tvType.text = report.type
                tvDateLoc.text = "${report.date} • ${report.location}"
                tvPoints.text = "+${report.points} pts"

                // Always use bitmap to avoid permission crashes with Uri
                if (report.imageBitmap != null) {
                    ivPhoto.setImageBitmap(report.imageBitmap)
                }

                when (report.type.lowercase()) {
                    "plastic" -> {
                        ivIcon.setImageResource(R.drawable.waterbottle)
                        ivIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F0F9F4"))
                        ivIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#0E9E6D"))
                    }
                    "organic" -> {
                        ivIcon.setImageResource(R.drawable.compost)
                        ivIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFF1F2"))
                        ivIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#A23546"))
                    }
                    "glass" -> {
                        ivIcon.setImageResource(R.drawable.glass34)
                        ivIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F0F4F8"))
                        ivIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#3B82F6"))
                    }
                    "paper" -> {
                        ivIcon.setImageResource(R.drawable.paperstack)
                        ivIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FEF9C3"))
                        ivIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#CA8A04"))
                    }
                    "electronic" -> {
                        ivIcon.setImageResource(R.drawable.ewaste)
                        ivIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F3E8FF"))
                        ivIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#7C3AED"))
                    }
                    "other" -> {
                        ivIcon.setImageResource(R.drawable.garbage)
                        ivIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F3F4F6"))
                        ivIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#4B5563"))
                    }
                }

                container.addView(itemView)
            }
        }
    }
}
