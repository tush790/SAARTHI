package com.inclusiveedu.app

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioButton
import android.widget.Toast
import com.inclusiveedu.app.databinding.ActivityQuizBinding
import com.inclusiveedu.app.models.QuizQuestion

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var quizzes: List<QuizQuestion>
    private val userAnswers = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedClass = intent.getStringExtra("class") ?: "Class 1"
        val selectedSubject = intent.getStringExtra("subject") ?: ""

        // Load quizzes for selected subject and class
        quizzes = getQuizzesForSubjectAndClass(selectedSubject, selectedClass)

        if (quizzes.isEmpty()) {
            Toast.makeText(this, "No quizzes available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Back button
        binding.backBtn.setOnClickListener {
            finish()
        }

        // Display first question
        displayQuestion()

        // Next button
        binding.nextBtn.setOnClickListener {
            val selectedOptionId = binding.optionsRadioGroup.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadioButton = findViewById<RadioButton>(selectedOptionId)
            val selectedAnswer = selectedRadioButton.text.toString()

            userAnswers[currentQuestionIndex] = selectedAnswer

            // Check if answer is correct
            if (selectedAnswer == quizzes[currentQuestionIndex].correctAnswer) {
                score++
            }

            currentQuestionIndex++

            if (currentQuestionIndex < quizzes.size) {
                displayQuestion()
            } else {
                showResults()
            }
        }
    }

    private fun displayQuestion() {
        val question = quizzes[currentQuestionIndex]
        binding.questionNumber.text = "Question ${currentQuestionIndex + 1}/${quizzes.size}"
        binding.questionText.text = question.question

        binding.optionsRadioGroup.clearCheck()
        binding.optionA.text = question.optionA
        binding.optionB.text = question.optionB
        binding.optionC.text = question.optionC
        binding.optionD.text = question.optionD
    }

    private fun showResults() {
        AlertDialog.Builder(this)
            .setTitle("Quiz Completed!")
            .setMessage("Your Score: $score/${quizzes.size}")
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .show()
    }

    private fun getQuizzesForSubjectAndClass(subject: String, className: String): List<QuizQuestion> {
        return when {
            subject.contains("English") -> getEnglishQuiz(className)
            subject.contains("Maths") -> getMathsQuiz(className)
            subject.contains("Hindi") -> getHindiQuiz(className)
            subject.contains("Marathi") -> getMarathiQuiz(className)
            subject.contains("General Knowledge") -> getGKQuiz(className)
            else -> emptyList()
        }
    }

    private fun getEnglishQuiz(className: String): List<QuizQuestion> {
        return listOf(
            QuizQuestion(1, "English", className, "What is the past tense of 'go'?",
                "A) Went", "B) Gone", "C) Going", "D) Goes", "A) Went"),
            QuizQuestion(2, "English", className, "Which word is a noun?",
                "A) Run", "B) Beautiful", "C) Dog", "D) Quickly", "C) Dog"),
            QuizQuestion(3, "English", className, "What does 'noun' mean?",
                "A) Action", "B) Person/Place/Thing", "C) Quality", "D) Verb", "B) Person/Place/Thing")
        )
    }

    private fun getMathsQuiz(className: String): List<QuizQuestion> {
        return listOf(
            QuizQuestion(1, "Maths", className, "What is 5 + 3?",
                "A) 7", "B) 8", "C) 9", "D) 10", "B) 8"),
            QuizQuestion(2, "Maths", className, "What is 10 × 2?",
                "A) 12", "B) 20", "C) 15", "D) 25", "B) 20"),
            QuizQuestion(3, "Maths", className, "What is 15 ÷ 3?",
                "A) 3", "B) 4", "C) 5", "D) 6", "C) 5")
        )
    }

    private fun getHindiQuiz(className: String): List<QuizQuestion> {
        return listOf(
            QuizQuestion(1, "Hindi", className, "हिंदी भाषा कहाँ बोली जाती है?",
                "A) सिर्फ भारत में", "B) भारत और नेपाल में", "C) विश्वभर में", "D) सिर्फ दिल्ली में", "B) भारत और नेपाल में"),
            QuizQuestion(2, "Hindi", className, "'पत्र' का पर्यायवाची शब्द क्या है?",
                "A) चिट्ठी", "B) पुस्तक", "C) कागज", "D) लिपि", "A) चिट्ठी"),
            QuizQuestion(3, "Hindi", className, "हिंदी वर्णमाला में कुल कितने व्यंजन हैं?",
                "A) 11", "B) 33", "C) 52", "D) 26", "B) 33")
        )
    }

    private fun getMarathiQuiz(className: String): List<QuizQuestion> {
        return listOf(
            QuizQuestion(1, "Marathi", className, "मराठी भाषा कहाँ बोली जाते?",
                "A) महाराष्ट्र में", "B) गुजरात में", "C) कर्नाटक में", "D) तेलंगाना में", "A) महाराष्ट्र में"),
            QuizQuestion(2, "Marathi", className, "'पुस्तक' चा पर्यायवाची शब्द कोणता आहे?",
                "A) ग्रंथ", "B) कागज", "C) लिहिली गोष्ट", "D) शब्द", "A) ग्रंथ"),
            QuizQuestion(3, "Marathi", className, "मराठीतून 'नमस्कार' चा अर्थ काय आहे?",
                "A) अलविदा", "B) अभिवादन", "C) धन्यवाद", "D) माफ करा", "B) अभिवादन")
        )
    }

    private fun getGKQuiz(className: String): List<QuizQuestion> {
        return listOf(
            QuizQuestion(1, "General Knowledge", className, "भारत की राजधानी क्या है?",
                "A) बम्बई", "B) दिल्ली", "C) कोलकाता", "D) चेन्नई", "B) दिल्ली"),
            QuizQuestion(2, "General Knowledge", className, "भारत में कुल कितने राज्य हैं?",
                "A) 25", "B) 28", "C) 30", "D) 32", "B) 28"),
            QuizQuestion(3, "General Knowledge", className, "पृथ्वी का सबसे बड़ा महासागर कौन सा है?",
                "A) अटलांटिक", "B) हिंद महासागर", "C) प्रशांत महासागर", "D) आर्कटिक", "C) प्रशांत महासागर")
        )
    }
}