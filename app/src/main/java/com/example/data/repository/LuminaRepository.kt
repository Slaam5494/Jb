package com.example.data.repository

import com.example.R
import com.example.data.model.ChatMessage
import com.example.data.model.CoinPackage
import com.example.data.model.DirectMessage
import com.example.data.model.FriendItem
import com.example.data.model.ImageGenItem
import com.example.data.model.MovieProject
import com.example.data.model.PromptTemplate
import com.example.data.model.StatusItem
import com.example.data.model.UserAccount
import com.example.data.model.VideoGenItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class LuminaRepository {

    private val _userAccount = MutableStateFlow(
        UserAccount(
            id = "LUMINA-7094",
            name = "Guest Operative",
            email = "guest@lumina.ai",
            isGuest = true,
            coins = 50.0,
            isVip = false,
            adsWatchedToday = 0
        )
    )
    val userAccount: StateFlow<UserAccount> = _userAccount.asStateFlow()

    private val _movieProjects = MutableStateFlow<List<MovieProject>>(
        listOf(
            MovieProject(
                id = "mov_1",
                title = "Cyber Genesis: The 9D Awakening",
                script = "An elite cyber operative navigates neon-drenched skies of Neo-Tokyo to recover the last quantum AI core before dusk.",
                durationMinutes = 15,
                bgmStyle = "Cinematic Action BGM",
                progress = 1.0f
            ),
            MovieProject(
                id = "mov_2",
                title = "Chronicles of Horizon Zero",
                script = "Deep space expedition uncovers an ancient alien monolith vibrating with synthetic harmonies.",
                durationMinutes = 5,
                bgmStyle = "Auto (AI Detects Scene Mood)",
                progress = 1.0f
            )
        )
    )
    val movieProjects: StateFlow<List<MovieProject>> = _movieProjects.asStateFlow()

    private val _videoList = MutableStateFlow<List<VideoGenItem>>(
        listOf(
            VideoGenItem(
                id = "vid_1",
                prompt = "Cyberpunk drone soaring through neo-Tokyo neon rain and holographic billboards",
                style = "Cinematic Matrix",
                aspectRatio = "16:9",
                durationSec = 10,
                cameraMotion = "Orbit 360",
                progress = 1.0f
            ),
            VideoGenItem(
                id = "vid_2",
                prompt = "Neon Lumina infinity core twisting inside a quantum gravity vortex",
                style = "Hologram 9D",
                aspectRatio = "9:16",
                durationSec = 5,
                cameraMotion = "Dolly Zoom",
                progress = 1.0f
            )
        )
    )
    val videoList: StateFlow<List<VideoGenItem>> = _videoList.asStateFlow()

    private val _imageList = MutableStateFlow<List<ImageGenItem>>(
        listOf(
            ImageGenItem(
                id = "img_1",
                prompt = "Cybernetic biomechanical panther glowing with cyan fiber optics and electric violet claws",
                style = "9D Cyberpunk",
                aspectRatio = "1:1",
                drawableRes = R.drawable.bg_video_gen
            ),
            ImageGenItem(
                id = "img_2",
                prompt = "Lumina neural engine core radiating intense blue-cyan spatial energy",
                style = "Neon Hologram",
                aspectRatio = "16:9",
                drawableRes = R.drawable.lumina_logo
            ),
            ImageGenItem(
                id = "img_3",
                prompt = "Futuristic cyber laboratory with floating holographic UI screens",
                style = "Photoreal AR",
                aspectRatio = "16:9",
                drawableRes = R.drawable.bg_image_gen
            )
        )
    )
    val imageList: StateFlow<List<ImageGenItem>> = _imageList.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "msg_0",
                isFromUser = false,
                text = "Neural Node initialized. Welcome to LUMINA AI Movie Maker Core. How can I assist your cinematic operations today?"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _promptTemplates = MutableStateFlow<List<PromptTemplate>>(
        listOf(
            PromptTemplate(
                id = "pt_1",
                title = "Feature Film Storyline & Screenplay",
                category = "Movie Maker",
                description = "Generates complete cinematic story acts with scene beats, dialog, and BGM cues.",
                template = "Write a full 3-act cinematic screenplay outline for [Title/Concept]. Include scene-by-scene visual descriptions, lighting palette, and emotional audio equalizer cues."
            ),
            PromptTemplate(
                id = "pt_2",
                title = "8K Cinematic Sci-Fi Master",
                category = "Video & 3D",
                description = "Generates hyper-detailed cinematic scenes with anamorphic lens and volumetric mist.",
                template = "Cinematic shot of [Subject], 8K resolution, octane render, volumetric lighting, Unreal Engine 5 aesthetic, neon reflections on wet asphalt, 35mm lens, photorealistic color grading."
            ),
            PromptTemplate(
                id = "pt_3",
                title = "Cyberpunk Character Portrait",
                category = "Image Gen",
                description = "Stylized neon portraits with intricate cyberware and glowing ocular implants.",
                template = "Intricate cyberpunk character portrait of [Name/Profession], glowing cyan ocular augments, holographic tattoo circuitry, rain droplets, atmospheric rim lighting, ultra sharp focus."
            ),
            PromptTemplate(
                id = "pt_4",
                title = "Viral AI Product Pitch",
                category = "Marketing",
                description = "Compelling, punchy copy tailored for high-converting landing pages.",
                template = "Write an electrifying launch announcement for [Product Name], emphasizing cutting-edge neural capabilities, exclusivity, and 10x productivity gains."
            )
        )
    )
    val promptTemplates: StateFlow<List<PromptTemplate>> = _promptTemplates.asStateFlow()

    private val _friends = MutableStateFlow<List<FriendItem>>(
        listOf(
            FriendItem(
                id = "f_0",
                name = "Lumina AI Companion",
                avatarUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=150&q=80",
                status = "Online • Always Active",
                isOnline = true,
                unreadCount = 0,
                lastMessage = "Your movie project rendering queue is active and ready!",
                lastMessageTime = "Now",
                phoneNumber = "+1 800 586 4621"
            ),
            FriendItem(
                id = "f_1",
                name = "Alex (VIP Member)",
                avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=150&q=80",
                status = "Last seen 5 mins ago",
                isOnline = true,
                unreadCount = 2,
                lastMessage = "Have you tested the 80 Min Feature Movie generator yet?",
                lastMessageTime = "10:42 AM",
                phoneNumber = "+1 415 555 0192"
            ),
            FriendItem(
                id = "f_2",
                name = "Dr. Elena Rostova",
                avatarUrl = "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&w=150&q=80",
                status = "Online • Quantum Audio Lab",
                isOnline = true,
                unreadCount = 0,
                lastMessage = "BGM equalizer presets tuned to high energy rock.",
                lastMessageTime = "09:15 AM",
                phoneNumber = "+1 212 555 8342"
            )
        )
    )
    val friends: StateFlow<List<FriendItem>> = _friends.asStateFlow()

    private val _statuses = MutableStateFlow<List<StatusItem>>(
        listOf(
            StatusItem(
                id = "st_1",
                friendName = "Lumina AI Companion",
                avatarUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=150&q=80",
                caption = "New AI Movie: Neon Cyber Highway premiered in 4K 🔥",
                timeAgo = "5 min ago"
            ),
            StatusItem(
                id = "st_2",
                friendName = "Alex (VIP Member)",
                avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=150&q=80",
                caption = "Testing full 80 min movie generation sequence 🚀",
                timeAgo = "30 min ago"
            )
        )
    )
    val statuses: StateFlow<List<StatusItem>> = _statuses.asStateFlow()

    val coinPackages = listOf(
        CoinPackage("pkg_1", 10.0, 0.0, "Rs. 100", "DAILY ECONOMY", "10 Coins (50 Sec Video / Calls) • 24 Hours"),
        CoinPackage("pkg_2", 50.0, 0.0, "Rs. 450", "WEEKLY ECONOMY", "50 Coins (~4 Min Video) • 7 Days"),
        CoinPackage("pkg_3", 250.0, 0.0, "Rs. 1,800", "MONTHLY STANDARD", "250 Coins (~20 Min Video) • 30 Days"),
        CoinPackage("pkg_4", 600.0, 0.0, "Rs. 4,000", "MONTHLY PRO", "600 Coins (~50 Min Video) • 30 Days", isPopular = true)
    )

    fun login(email: String, name: String, isGuest: Boolean) {
        _userAccount.update {
            it.copy(
                name = name,
                email = email,
                isGuest = isGuest,
                coins = if (isGuest) it.coins else it.coins + 25.0
            )
        }
    }

    fun deductCoins(amount: Double): Boolean {
        if (_userAccount.value.coins >= amount) {
            _userAccount.update { it.copy(coins = (it.coins - amount).coerceAtLeast(0.0)) }
            return true
        }
        return false
    }

    fun addCoins(amount: Double) {
        _userAccount.update { it.copy(coins = it.coins + amount) }
    }

    fun incrementAdsWatched(): Boolean {
        if (_userAccount.value.adsWatchedToday >= 3) {
            return false
        }
        _userAccount.update {
            it.copy(
                adsWatchedToday = it.adsWatchedToday + 1,
                coins = it.coins + 1.0
            )
        }
        return true
    }

    fun addMovieProject(project: MovieProject) {
        _movieProjects.update { listOf(project) + it }
    }

    fun addVideo(video: VideoGenItem) {
        _videoList.update { listOf(video) + it }
    }

    fun addImage(image: ImageGenItem) {
        _imageList.update { listOf(image) + it }
    }

    fun addChatMessage(message: ChatMessage) {
        _chatMessages.update { it + message }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                isFromUser = false,
                text = "LUMINA AI Movie Maker buffer reset. Ready for next cinematic command."
            )
        )
    }

    fun saveStatus(statusId: String) {
        _statuses.update { list ->
            list.map { if (it.id == statusId) it.copy(isSaved = true) else it }
        }
    }
}
