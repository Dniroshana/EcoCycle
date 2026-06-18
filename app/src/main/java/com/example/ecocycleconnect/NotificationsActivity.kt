package com.example.ecocycleconnect

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notifications)

        val header = findViewById<View>(R.id.top_header)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val density = resources.displayMetrics.density
                val verticalPadding = (8 * density).toInt()
                val sidePadding = (16 * density).toInt()
                
                v.setPadding(
                    sidePadding, 
                    systemBars.top + verticalPadding, 
                    sidePadding, 
                    verticalPadding
                )
                insets
            }
        }

        findViewById<View>(R.id.btn_back)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<TextView>(R.id.btn_clear_all)?.setOnClickListener {
            Toast.makeText(this, "Notifications cleared", Toast.LENGTH_SHORT).show()
        }
    }
}
