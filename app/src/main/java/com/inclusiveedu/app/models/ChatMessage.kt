package com.inclusiveedu.app.models

data class ChatMessage(
    val id: Int,
    val message: String,
    val isSentByUser: Boolean,  // true = user, false = chatbot
    val timestamp: Long = System.currentTimeMillis()
)