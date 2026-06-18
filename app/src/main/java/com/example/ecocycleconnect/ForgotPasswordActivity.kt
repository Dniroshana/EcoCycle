package com.example.ecocycleconnect

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        
        val btnBack = findViewById<ConstraintLayout>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val etForgotEmail = findViewById<EditText>(R.id.etForgotEmail)
        val btnSendResetLink = findViewById<MaterialButton>(R.id.btnSendResetLink)

        btnSendResetLink.setOnClickListener {
            val email = etForgotEmail.text.toString().trim()

            if (email.isEmpty()) {
                etForgotEmail.error = "Email is required"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etForgotEmail.error = "Please enter a valid email address"
            } else {
                // Email is valid, navigate to Reset Password screen
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }
}