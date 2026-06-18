package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)

        val btnBack = findViewById<ConstraintLayout>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnUpdatePassword = findViewById<MaterialButton>(R.id.btnUpdatePassword)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        btnUpdatePassword.setOnClickListener {
            val newPassword = etNewPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (newPassword.isEmpty()) {
                etNewPassword.error = "Password cannot be empty"
            } else if (newPassword.length < 8) {
                etNewPassword.error = "Password must be at least 8 characters"
            } else if (confirmPassword.isEmpty()) {
                etConfirmPassword.error = "Please confirm your password"
            } else if (newPassword != confirmPassword) {
                etConfirmPassword.error = "Passwords do not match"
            } else {
                // Navigate to Success screen
                val intent = Intent(this, ResetPasswordSuccessActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}