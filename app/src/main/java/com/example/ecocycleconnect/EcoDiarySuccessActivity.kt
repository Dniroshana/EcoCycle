package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class EcoDiarySuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eco_diary_success)

        val entryText = intent.getStringExtra("ENTRY_TEXT")
        val entryDate = intent.getStringExtra("ENTRY_DATE")

        findViewById<View>(R.id.btn_view_history).setOnClickListener {
            val intent = Intent(this, EcoDiaryActivity::class.java)
            intent.putExtra("ENTRY_TEXT", entryText)
            intent.putExtra("ENTRY_DATE", entryDate)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        findViewById<View>(R.id.btn_back_dashboard).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}