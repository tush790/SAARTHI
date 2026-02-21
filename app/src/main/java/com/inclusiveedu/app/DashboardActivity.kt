package com.inclusiveedu.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.inclusiveedu.app.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get selected class from intent (default to Class 1)
        val selectedClass = intent.getStringExtra("class") ?: "Class 1"
        binding.classNameText.text = "📚 $selectedClass"

        // Syllabus Button
        binding.syllabusCard.setOnClickListener {
            val intent = Intent(this, SubjectActivity::class.java)
            intent.putExtra("class", selectedClass)
            startActivity(intent)
        }

        // Quiz Button
        binding.quizCard.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("class", selectedClass)
            startActivity(intent)
        }

        // Progress Button
        binding.progressCard.setOnClickListener {
            val intent = Intent(this, ProgressActivity::class.java)
            intent.putExtra("class", selectedClass)
            startActivity(intent)
        }

        // User Data Button (Top Right)
        binding.userDataBtn.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        // Back button (Logout)
        binding.backBtn.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Clear user data
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                sharedPref.edit().remove("user_id").apply()
                sharedPref.edit().remove("username").apply()

                // Go back to login
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}