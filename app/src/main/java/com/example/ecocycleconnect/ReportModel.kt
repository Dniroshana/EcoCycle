package com.example.ecocycleconnect

import android.graphics.Bitmap
import android.net.Uri

data class WasteReport(
    val type: String,
    val date: String,
    val location: String,
    val points: Int,
    val imageUri: Uri? = null,
    val imageBitmap: Bitmap? = null
)

object ReportRepository {
    val reports = mutableListOf<WasteReport>()

    fun addReport(report: WasteReport) {
        reports.add(0, report) // Add to top
        if (reports.size > 5) {
            reports.removeAt(reports.lastIndex) // Keep only the latest 5 entries
        }
    }
}
