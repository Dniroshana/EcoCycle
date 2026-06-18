package com.example.ecocycleconnect

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoogleAccountsActivity : AppCompatActivity() {

    private lateinit var rvAccounts: RecyclerView
    private val PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_google_accounts)

        rvAccounts = findViewById(R.id.rvAccounts)
        rvAccounts.layoutManager = LinearLayoutManager(this)

        checkPermissionsAndLoad()
    }

    private fun checkPermissionsAndLoad() {
        val permissions = arrayOf(
            android.Manifest.permission.GET_ACCOUNTS,
            android.Manifest.permission.READ_CONTACTS
        )

        val neededPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (neededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, neededPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        } else {
            loadAccounts()
        }
    }

    private fun loadAccounts() {
        val googleAccounts = mutableListOf<GoogleAccount>()
        
        try {
            val accountManager = AccountManager.get(this)
            val accounts: Array<Account> = accountManager.getAccountsByType("com.google")
            
            accounts.forEach { account ->
                val accountName = account.name.split("@")[0].replaceFirstChar { it.uppercase() }
                googleAccounts.add(GoogleAccount(accountName, account.name))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // --- FALLBACK: If no accounts found due to security restrictions, show sample data ---
        if (googleAccounts.isEmpty()) {
            googleAccounts.add(GoogleAccount("Hashini Samuel", "Hashini.Samuel@gmail.com"))
            googleAccounts.add(GoogleAccount("Shehani Jayakody", "Shehani.jay@gmail.com"))
            Toast.makeText(this, "Showing sample accounts for UI testing", Toast.LENGTH_SHORT).show()
        }

        val adapter = AccountAdapter(googleAccounts) { selectedAccount ->
            // Save selected account details to SharedPreferences (Simulating Login)
            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            sharedPref.edit {
                putString("name", selectedAccount.name)
                putString("email", selectedAccount.email)
            }

            Toast.makeText(this, "Logged in as ${selectedAccount.email}", Toast.LENGTH_SHORT).show()

            // Navigate to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        rvAccounts.adapter = adapter
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadAccounts()
        }
    }
}