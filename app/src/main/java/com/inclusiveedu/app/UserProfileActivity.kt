package com.inclusiveedu.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.inclusiveedu.app.data.AppDatabase
import com.inclusiveedu.app.databinding.ActivityUserProfileBinding
import kotlinx.coroutines.launch

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        // Back button
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Load user profile
        loadUserProfile()
    }

    private fun loadUserProfile() {
        lifecycleScope.launch {
            try {
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val userId = sharedPref.getInt("user_id", -1)

                if (userId != -1) {
                    val user = database.userDao().getUserById(userId)

                    if (user != null) {
                        // Display user profile
                        binding.userName.text = user.username
                        binding.userEmail.text = user.email
                        binding.userProfile.text = """
                            👤 Profile Information
                            
                            Username: ${user.username}
                            📧 Email: ${user.email}
                            
                            🎓 Learning Progress
                            ✅ Quizzes: 0/15
                            📚 Lessons: 0/20
                            
                            🏆 Achievements
                            ⭐ Current Level: Beginner
                            
                            Member Since: Today
                        """.trimIndent()
                    }
                }
            } catch (e: Exception) {
                binding.userProfile.text = "Error loading profile: ${e.message}"
            }
        }
    }
}