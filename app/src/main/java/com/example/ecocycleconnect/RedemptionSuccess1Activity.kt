package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RedemptionSuccess1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_redemption_success1)

        // Status bar එකට අනුව header එක සකස් කිරීම
        val header = findViewById<LinearLayout>(R.id.top_header)
        header?.let { v ->
            ViewCompat.setOnApplyWindowInsetsListener(v) { view, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(view.paddingLeft, systemBars.top, view.paddingRight, view.paddingBottom)
                insets
            }
        }

        // Header එකේ ඇති Back button එක
        findViewById<LinearLayout>(R.id.btn_back_header)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // View Voucher Button - Opens Contribution Details screen
        findViewById<MaterialButton>(R.id.btn_view_voucher)?.setOnClickListener {
            val intent = Intent(this, ContributionDetailsActivity::class.java)
            startActivity(intent)
        }

        // Back to Rewards Button
        findViewById<MaterialButton>(R.id.btn_back_to_rewards)?.setOnClickListener {
            val intent = Intent(this, RewardsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
