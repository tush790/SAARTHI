package com.inclusiveedu.app

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Button
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.inclusiveedu.app.databinding.ActivityLessonBinding

class LessonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding

    private val classes = listOf("Class 1", "Class 2", "Class 3", "Class 4")
    private val classColors = listOf(
        R.color.maths_color,
        R.color.english_color,
        R.color.hindi_color,
        R.color.gk_color
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get subject from intent
        val subject = intent.getStringExtra("subject") ?: "Subject"
        binding.subjectName.text = subject

        // Back button
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Clear existing buttons and add class buttons
        binding.classContainer.removeAllViews()

        for (i in classes.indices) {
            val classButton = createClassButton(classes[i], classColors[i], subject)
            binding.classContainer.addView(classButton)
        }
    }

    private fun createClassButton(className: String, colorResId: Int, subject: String): CardView {
        val cardView = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(100)
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
            text = "📚 $className"
            textSize = 20f
            setTextColor(android.graphics.Color.WHITE)
            setBackgroundColor(resources.getColor(colorResId, null))
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))

            setOnClickListener {
                // TODO: Open lessons or content for this class
            }
        }

        cardView.addView(button)
        return cardView
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}