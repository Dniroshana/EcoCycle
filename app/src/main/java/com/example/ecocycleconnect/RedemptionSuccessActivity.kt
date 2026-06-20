package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RedemptionSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_redemption_success)

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
            val intent = Intent(this, RewardsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        findViewById<MaterialButton>(R.id.btn_view_voucher)?.setOnClickListener {
            val intent = Intent(this, VoucherDetailsActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.btn_back_to_rewards)?.setOnClickListener {
            val intent = Intent(this, RewardsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
