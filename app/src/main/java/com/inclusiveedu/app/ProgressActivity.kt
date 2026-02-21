package com.inclusiveedu.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.inclusiveedu.app.data.AppDatabase
import com.inclusiveedu.app.databinding.ActivityProgressBinding
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        val selectedClass = intent.getStringExtra("class") ?: "Class 1"
        binding.className.text = "📚 $selectedClass - Progress"

        // Back button
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Load user progress
        loadProgress()
    }

    private fun loadProgress() {
        lifecycleScope.launch {
            try {
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val userId = sharedPref.getInt("user_id", -1)

                if (userId != -1) {
                    val user = database.userDao().getUserById(userId)

                    if (user != null) {
                        // Display user progress
                        binding.progressInfo.text = """
                            📊 Progress Report
                            
                            👤 User: ${user.username}
                            📧 Email: ${user.email}
                            🎓 Class: ${intent.getStringExtra("class") ?: "Class 1"}
                            
                            ✅ Quizzes Completed: 0
                            📈 Overall Score: 0%
                            📚 Lessons Completed: 0
                            
                            Last Updated: Today
                        """.trimIndent()
                    }
                }
            } catch (e: Exception) {
                binding.progressInfo.text = "Error loading progress: ${e.message}"
            }
        }
    }
}