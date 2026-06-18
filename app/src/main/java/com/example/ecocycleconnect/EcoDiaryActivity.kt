package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class EcoDiaryActivity : AppCompatActivity() {

    private lateinit var historyItemsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eco_diary)

        historyItemsContainer = findViewById(R.id.history_items_container)

        findViewById<View>(R.id.btn_back)?.setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.button___fab)?.setOnClickListener {
            val intent = Intent(this, EditEcoDiaryActivity::class.java)
            startActivity(intent)
        }

        // Check if we have new entry data
        val entryText = intent.getStringExtra("ENTRY_TEXT")
        val entryDate = intent.getStringExtra("ENTRY_DATE")

        if (entryText != null && entryDate != null) {
            addNewEntryToHistory(entryText, entryDate)
        }
    }

    private fun addNewEntryToHistory(text: String, date: String) {
        val inflater = LayoutInflater.from(this)
        val itemView = inflater.inflate(R.layout.item_diary_entry, historyItemsContainer, false)

        val tvTitle = itemView.findViewById<TextView>(R.id.tv_entry_title)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_entry_date)
        val tvDescription = itemView.findViewById<TextView>(R.id.tv_entry_description)
        val btnEdit = itemView.findViewById<ImageView>(R.id.btn_edit_entry)

        tvTitle.text = "Recent Recycling Activity"
        tvDate.text = "Date: $date"
        tvDescription.text = text

        btnEdit.setOnClickListener {
            val intent = Intent(this, EditEcoDiaryActivity::class.java)
            intent.putExtra("ENTRY_TEXT", text)
            intent.putExtra("ENTRY_DATE", date)
            startActivity(intent)
        }

        historyItemsContainer.addView(itemView, 0) // Add to the top of the list
    }
}