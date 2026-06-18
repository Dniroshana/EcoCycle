package com.example.ecocycleconnect

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DeleteEcoDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_eco_diary)

        findViewById<Button>(R.id.btn_confirm_delete).setOnClickListener {
            // Logic to delete the entry would go here
            Toast.makeText(this, "Entry deleted successfully", Toast.LENGTH_SHORT).show()
            
            // Go back to the diary list or previous screens
            // For now, let's just finish this and the calling activity
            setResult(RESULT_OK)
            finish()
        }

        findViewById<Button>(R.id.btn_cancel_delete).setOnClickListener {
            finish()
        }
        
        // Background click to finish (optional, since it looks like a dialog)
        findViewById<View>(android.R.id.content).setOnClickListener {
            // finish() // Uncomment if you want clicking outside to close
        }
    }
}