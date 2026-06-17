package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class DeleteAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_account)

        val header = findViewById<LinearLayout>(R.id.header)
        if (header != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        // Fix: Changed ImageView to LinearLayout to match XML
        val btnBack = findViewById<LinearLayout>(R.id.btn_back)
        btnBack?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnCancel = findViewById<MaterialButton>(R.id.btn_cancel)
        btnCancel?.setOnClickListener {
            finish()
        }

        val btnConfirmDelete = findViewById<MaterialButton>(R.id.btn_confirm_delete)
        btnConfirmDelete?.setOnClickListener {
            // Start Success Activity - Fixed to go to DeleteAccountSuccessActivity
            val intent = Intent(this, DeleteAccountSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
