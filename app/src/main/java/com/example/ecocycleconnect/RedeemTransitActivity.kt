package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RedeemTransitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_redeem_transit)

        val header = findViewById<LinearLayout>(R.id.top_header)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val density = resources.displayMetrics.density
                val verticalPadding = (12 * density).toInt()
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

        findViewById<LinearLayout>(R.id.btn_back)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<MaterialButton>(R.id.btn_confirm_redemption)?.setOnClickListener {
            val intent = Intent(this, RedemptionSuccess3Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
