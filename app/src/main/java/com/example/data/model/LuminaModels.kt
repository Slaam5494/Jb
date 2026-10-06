package com.example.data.model

data class UserAccount(
    val id: String = "LUMINA-7094",
    val name: String = "Cyber Operative",
    val email: String = "guest@lumina.ai",
    val isGuest: Boolean = true,
    val coins: Double = 50.0,
    val isVip: Boolean = false,
    val avatarUrl: String = "",
    val adsWatchedToday: Int = 0
)

enum class ModuleType {
    DASHBOARD,
    MOVIE_MAKER,
    MEDIA_GEN,
    AI_CHAT,
    PROMPT_STUDIO,
    FRIENDS_HUB,
    SETTINGS
}

data class MovieProject(
    val id: String,
    val title: String,
    val script: String,
    val durationMinutes: Int,
    val bgmStyle: String,
    val isGenerating: Boolean = false,
    val progress: Float = 1.0f,
    val createdAt: Long = System.currentTimeMillis()
)

data class VideoGenItem(
    val id: String,
    val prompt: String,
    val style: String,
    val aspectRatio: String,
    val durationSec: Int,
    val cameraMotion: String,
    val isGenerating: Boolean = false,
    val progress: Float = 1.0f,
    val createdAt: Long = System.currentTimeMillis()
)

data class ImageGenItem(
    val id: String,
    val prompt: String,
    val style: String,
    val aspectRatio: String,
    val drawableRes: Int? = null,
    val isGenerating: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class ChatMessage(
    val id: String,
    val isFromUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val model: String = "Gemini 3.5 Flash"
)

data class PromptTemplate(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val template: String
)

data class FriendItem(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val status: String,
    val isOnline: Boolean,
    val unreadCount: Int,
    val lastMessage: String,
    val lastMessageTime: String,
    val phoneNumber: String = "+1 555 982 4110"
)

data class DirectMessage(
    val id: String,
    val text: String,
    val time: String,
    val isMe: Boolean
)

data class StatusItem(
    val id: String,
    val friendName: String,
    val avatarUrl: String,
    val caption: String,
    val timeAgo: String,
    val isSaved: Boolean = false
)

data class CoinPackage(
    val id: String,
    val coins: Double,
    val bonus: Double,
    val priceText: String,
    val title: String,
    val validityText: String,
    val isPopular: Boolean = false
)
