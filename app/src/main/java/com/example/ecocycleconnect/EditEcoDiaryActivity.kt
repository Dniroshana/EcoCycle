package com.example.ecocycleconnect

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class EditEcoDiaryActivity : AppCompatActivity() {

    private lateinit var attachmentContainer: View
    private lateinit var imgPreview: ImageView
    private lateinit var placeholderContent: LinearLayout
    private lateinit var datePickerContainer: View
    private lateinit var tvLogDate: TextView
    private lateinit var etEntry: EditText
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            imgPreview.setImageURI(it)
            imgPreview.visibility = View.VISIBLE
            placeholderContent.visibility = View.GONE
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show()
        }
    }

    private val deleteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // If entry was deleted, close this screen too
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_eco_diary)

        attachmentContainer = findViewById(R.id.attachment_container)
        imgPreview = findViewById(R.id.img_preview)
        placeholderContent = findViewById(R.id.placeholder_content)
        datePickerContainer = findViewById(R.id.date_picker_container)
        tvLogDate = findViewById(R.id.tv_log_date)
        etEntry = findViewById(R.id.et_entry)

        // Pre-fill if editing existing entry
        val existingText = intent.getStringExtra("ENTRY_TEXT")
        val existingDate = intent.getStringExtra("ENTRY_DATE")
        if (existingText != null) etEntry.setText(existingText)
        if (existingDate != null) tvLogDate.text = existingDate

        findViewById<View>(R.id.btn_back_edit)?.setOnClickListener {
            finish()
        }

        // DELETE BUTTON CLICK
        findViewById<View>(R.id.btn_delete)?.setOnClickListener {
            val intent = Intent(this, DeleteEcoDiaryActivity::class.java)
            deleteLauncher.launch(intent)
        }

        findViewById<View>(R.id.btn_update_entry)?.setOnClickListener {
            val intent = Intent(this, EcoDiarySuccessActivity::class.java)
            intent.putExtra("ENTRY_TEXT", etEntry.text.toString())
            intent.putExtra("ENTRY_DATE", tvLogDate.text.toString())
            startActivity(intent)
            finish()
        }

        attachmentContainer.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        datePickerContainer.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val dateString = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                tvLogDate.text = dateString
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
