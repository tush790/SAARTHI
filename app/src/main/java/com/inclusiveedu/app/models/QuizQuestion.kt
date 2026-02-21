package com.inclusiveedu.app.models

data class QuizQuestion(
    val id: Int,
    val subject: String,
    val className: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String
)

data class QuizData(
    val subject: String,
    val questions: List<QuizQuestion>
)