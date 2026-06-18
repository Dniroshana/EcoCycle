package com.example.ecocycleconnect

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContributionDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contribution_details)

        val header = findViewById<LinearLayout>(R.id.top_header)
        header?.let { v ->
            ViewCompat.setOnApplyWindowInsetsListener(v) { view, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(view.paddingLeft, systemBars.top, view.paddingRight, view.paddingBottom)
                insets
            }
        }

        findViewById<LinearLayout>(R.id.btn_back)?.setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.having_trouble_scanning__contact_support)?.setOnClickListener {
            Toast.makeText(this, "Contacting Support...", Toast.LENGTH_SHORT).show()
        }
        
        // You can add more click listeners for project location items if needed
    }
}
