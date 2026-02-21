package com.inclusiveedu.app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Button
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.inclusiveedu.app.databinding.ActivitySubjectBinding

class SubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubjectBinding

    data class Subject(val name: String, val emoji: String, val colorResId: Int)

    private val subjects = listOf(
        Subject("English", "📖", R.color.english_color),
        Subject("Maths", "📐", R.color.maths_color),
        Subject("Hindi", "🇮🇳", R.color.hindi_color),
        Subject("Marathi", "🎭", R.color.marathi_color),
        Subject("General Knowledge", "🌍", R.color.gk_color)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Get selected class
        val selectedClass = intent.getStringExtra("class") ?: "Class 1"

        // Clear existing buttons and add new ones
        binding.subjectContainer.removeAllViews()

        // Add subject cards dynamically
        for (subject in subjects) {
            val cardView = createSubjectCard(subject, selectedClass)
            binding.subjectContainer.addView(cardView)
        }
    }

    private fun createSubjectCard(subject: Subject, selectedClass: String): CardView {
        val cardView = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(120)
            ).apply {
                bottomMargin = dpToPx(16)
            }
            radius = dpToPx(12).toFloat()
            elevation = dpToPx(4).toFloat()
        }

        val button = Button(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            text = "${subject.emoji} ${subject.name}"
            textSize = 20f
            setTextColor(android.graphics.Color.WHITE)
            setBackgroundColor(resources.getColor(subject.colorResId, null))
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))

            setOnClickListener {
                val intent = Intent(this@SubjectActivity, LessonActivity::class.java)
                intent.putExtra("subject", "${subject.emoji} ${subject.name}")
                intent.putExtra("class", selectedClass)
                startActivity(intent)
            }
        }

        cardView.addView(button)
        return cardView
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}