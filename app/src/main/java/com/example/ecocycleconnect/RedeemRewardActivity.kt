package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RedeemRewardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_redeem_reward)

        val header = findViewById<LinearLayout>(R.id.top_header)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val density = resources.displayMetrics.density
                
                // Header එකේ පෑඩින් එක ගානට සකස් කරමු
                // Top padding status bar එකට අනුව
                val topPadding = systemBars.top + (12 * density).toInt()
                // Bottom padding එක අඩු කරමු (gap එක අඩු කිරීමට)
                val bottomPadding = (4 * density).toInt()
                val sidePadding = (16 * density).toInt()
                
                v.setPadding(sidePadding, topPadding, sidePadding, bottomPadding)
                insets
            }
        }

        findViewById<LinearLayout>(R.id.btn_back)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<MaterialButton>(R.id.btn_confirm_redemption)?.setOnClickListener {
            // Navigate to Success Screen
            val intent = Intent(this, RedemptionSuccessActivity::class.java)
            startActivity(intent)
            finish() // Optional: Closes the redemption screen so user can't go back to it
        }
    }
}
