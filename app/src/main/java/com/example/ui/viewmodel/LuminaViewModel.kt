package com.example.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.api.GeminiApiHelper
import com.example.data.model.ChatMessage
import com.example.data.model.DirectMessage
import com.example.data.model.FriendItem
import com.example.data.model.ImageGenItem
import com.example.data.model.ModuleType
import com.example.data.model.MovieProject
import com.example.data.model.StatusItem
import com.example.data.model.UserAccount
import com.example.data.model.VideoGenItem
import com.example.data.repository.LuminaRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class LuminaViewModel(
    private val repository: LuminaRepository = LuminaRepository()
) : ViewModel() {

    private val RESTRICTED_TERMS = listOf("naked", "nudity", "sex", "explicit", "undress", "bikini", "adult", "erotic")

    val userAccount: StateFlow<UserAccount> = repository.userAccount
    val movieProjects: StateFlow<List<MovieProject>> = repository.movieProjects
    val videoList: StateFlow<List<VideoGenItem>> = repository.videoList
    val imageList: StateFlow<List<ImageGenItem>> = repository.imageList
    val chatMessages: StateFlow<List<ChatMessage>> = repository.chatMessages
    val promptTemplates = repository.promptTemplates
    val friends: StateFlow<List<FriendItem>> = repository.friends
    val statuses: StateFlow<List<StatusItem>> = repository.statuses
    val coinPackages = repository.coinPackages

    // Navigation and screen state
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentModule = MutableStateFlow(ModuleType.DASHBOARD)
    val currentModule: StateFlow<ModuleType> = _currentModule.asStateFlow()

    // Store dialog
    private val _isCoinStoreOpen = MutableStateFlow(false)
    val isCoinStoreOpen: StateFlow<Boolean> = _isCoinStoreOpen.asStateFlow()

    // Rewarded Ad state
    private val _isAdPlaying = MutableStateFlow(false)
    val isAdPlaying: StateFlow<Boolean> = _isAdPlaying.asStateFlow()

    private val _adCountdown = MutableStateFlow(5)
    val adCountdown: StateFlow<Int> = _adCountdown.asStateFlow()

    // Calling simulation
    private val _activeCallFriend = MutableStateFlow<FriendItem?>(null)
    val activeCallFriend: StateFlow<FriendItem?> = _activeCallFriend.asStateFlow()

    private val _isVideoCall = MutableStateFlow(false)
    val isVideoCall: StateFlow<Boolean> = _isVideoCall.asStateFlow()

    private val _callSeconds = MutableStateFlow(0)
    val callSeconds: StateFlow<Int> = _callSeconds.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _isSpeakerOn = MutableStateFlow(true)
    val isSpeakerOn: StateFlow<Boolean> = _isSpeakerOn.asStateFlow()

    // Friends Direct Messaging
    private val _activeChatFriend = MutableStateFlow<FriendItem?>(null)
    val activeChatFriend: StateFlow<FriendItem?> = _activeChatFriend.asStateFlow()

    private val _directMessages = MutableStateFlow<List<DirectMessage>>(emptyList())
    val directMessages: StateFlow<List<DirectMessage>> = _directMessages.asStateFlow()

    // Movie generation state
    private val _isMovieGenerating = MutableStateFlow(false)
    val isMovieGenerating: StateFlow<Boolean> = _isMovieGenerating.asStateFlow()

    private val _movieGenProgress = MutableStateFlow(0f)
    val movieGenProgress: StateFlow<Float> = _movieGenProgress.asStateFlow()

    // Video generation state
    private val _isVideoGenerating = MutableStateFlow(false)
    val isVideoGenerating: StateFlow<Boolean> = _isVideoGenerating.asStateFlow()

    private val _videoGenProgress = MutableStateFlow(0f)
    val videoGenProgress: StateFlow<Float> = _videoGenProgress.asStateFlow()

    // Image generation state
    private val _isImageGenerating = MutableStateFlow(false)
    val isImageGenerating: StateFlow<Boolean> = _isImageGenerating.asStateFlow()

    // Chat loading
    private val _isChatGenerating = MutableStateFlow(false)
    val isChatGenerating: StateFlow<Boolean> = _isChatGenerating.asStateFlow()

    // Toast/Feedback events
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun clearToast() { _toastMessage.value = null }

    private fun isUnsafe(text: String): Boolean {
        val lower = text.lowercase()
        return RESTRICTED_TERMS.any { lower.contains(it) }
    }

    // Authentication
    fun loginWithGoogle() {
        repository.login(
            email = "operative@lumina.ai",
            name = "Alex (VIP Member)",
            isGuest = false
        )
        _isLoggedIn.value = true
        _toastMessage.value = "Neural ID Verified: Welcome Alex (+25 Bonus Coins)"
    }

    fun loginAsGuest() {
        repository.login(
            email = "guest@lumina.ai",
            name = "Operative-7094",
            isGuest = true
        )
        _isLoggedIn.value = true
        _toastMessage.value = "Guest Operative Mode Engaged"
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentModule.value = ModuleType.DASHBOARD
    }

    // Navigation
    fun navigateTo(module: ModuleType) {
        _currentModule.value = module
    }

    fun openCoinStore() {
        _isCoinStoreOpen.value = true
    }

    fun closeCoinStore() {
        _isCoinStoreOpen.value = false
    }

    // Monetization: Purchase Coins
    fun processPayment(gateway: String, pkgId: String) {
        val pkg = coinPackages.find { it.id == pkgId } ?: return
        repository.addCoins(pkg.coins)
        _isCoinStoreOpen.value = false
        _toastMessage.value = "[GATEWAY $gateway] Added ${pkg.coins.toInt()} Coins (${pkg.priceText}) to Wallet!"
    }

    // Monetization: Watch Rewarded Ad (Daily 3 Ads Limit)
    fun watchRewardedAd() {
        if (userAccount.value.adsWatchedToday >= 3) {
            _toastMessage.value = "Daily Free Ads Limit Reached (3 Ads). Please Buy Package!"
            _isCoinStoreOpen.value = true
            return
        }

        viewModelScope.launch {
            _isAdPlaying.value = true
            _adCountdown.value = 5
            for (i in 5 downTo 1) {
                _adCountdown.value = i
                delay(1000)
            }
            _isAdPlaying.value = false
            val success = repository.incrementAdsWatched()
            if (success) {
                _toastMessage.value = "+1.0 Coin Added! (Today: ${userAccount.value.adsWatchedToday}/3)"
            }
        }
    }

    // AI MOVIE MAKER (1 to 80 Min AI Movies & BGM)
    fun buildMovieProject(script: String, durationMinutes: Int, bgmStyle: String) {
        if (isUnsafe(script)) {
            _toastMessage.value = "⚠️ Safety Guard Active: NSFW or explicit prompts are strictly prohibited."
            return
        }
        if (script.isBlank()) {
            _toastMessage.value = "Please enter a movie storyline script!"
            return
        }

        val coinCost = when (durationMinutes) {
            1 -> 2.0
            5 -> 5.0
            15 -> 10.0
            30 -> 15.0
            else -> 25.0 // 80 Min Full Feature
        }

        if (!repository.deductCoins(coinCost)) {
            _toastMessage.value = "Insufficient Coins! Building $durationMinutes Min movie requires $coinCost Coins."
            _isCoinStoreOpen.value = true
            return
        }

        viewModelScope.launch {
            _isMovieGenerating.value = true
            _movieGenProgress.value = 0.05f
            val steps = 20
            for (i in 1..steps) {
                delay(150)
                _movieGenProgress.value = (i.toFloat() / steps)
            }
            val titleWords = script.split(" ").take(4).joinToString(" ")
            val title = if (titleWords.isNotBlank()) titleWords else "AI Feature Project"
            val newProject = MovieProject(
                id = UUID.randomUUID().toString(),
                title = title,
                script = script,
                durationMinutes = durationMinutes,
                bgmStyle = bgmStyle,
                progress = 1.0f
            )
            repository.addMovieProject(newProject)
            _isMovieGenerating.value = false
            _toastMessage.value = "🎬 9D Movie Project Generated ($durationMinutes Min) with $bgmStyle BGM!"
        }
    }

    // Media Generator - Video
    fun generateVideo(prompt: String, style: String, ratio: String, duration: Int, motion: String) {
        if (isUnsafe(prompt)) {
            _toastMessage.value = "⚠️ Safety Guard Active: NSFW or explicit prompts are strictly prohibited."
            return
        }
        if (prompt.isBlank()) {
            _toastMessage.value = "Please enter a video prompt description."
            return
        }
        val cost = 2.0
        if (!repository.deductCoins(cost)) {
            _toastMessage.value = "Insufficient Coins! Video Gen requires $cost Coins."
            _isCoinStoreOpen.value = true
            return
        }

        viewModelScope.launch {
            _isVideoGenerating.value = true
            _videoGenProgress.value = 0.05f
            val steps = 20
            for (i in 1..steps) {
                delay(120)
                _videoGenProgress.value = (i.toFloat() / steps)
            }
            val newVideo = VideoGenItem(
                id = UUID.randomUUID().toString(),
                prompt = prompt,
                style = style,
                aspectRatio = ratio,
                durationSec = duration,
                cameraMotion = motion,
                progress = 1.0f
            )
            repository.addVideo(newVideo)
            _isVideoGenerating.value = false
            _toastMessage.value = "9D Video Rendered Successfully!"
        }
    }

    // Media Generator - Image
    fun generateImage(prompt: String, style: String, ratio: String) {
        if (isUnsafe(prompt)) {
            _toastMessage.value = "⚠️ Safety Guard Active: NSFW or explicit prompts are strictly prohibited."
            return
        }
        if (prompt.isBlank()) {
            _toastMessage.value = "Please enter an image prompt description."
            return
        }
        val cost = 1.0
        if (!repository.deductCoins(cost)) {
            _toastMessage.value = "Insufficient Coins! Image Gen requires $cost Coins."
            _isCoinStoreOpen.value = true
            return
        }

        viewModelScope.launch {
            _isImageGenerating.value = true
            delay(1800)
            val drawableRes = when ((1..3).random()) {
                1 -> R.drawable.bg_video_gen
                2 -> R.drawable.bg_image_gen
                else -> R.drawable.lumina_logo
            }
            val newImage = ImageGenItem(
                id = UUID.randomUUID().toString(),
                prompt = prompt,
                style = style,
                aspectRatio = ratio,
                drawableRes = drawableRes
            )
            repository.addImage(newImage)
            _isImageGenerating.value = false
            _toastMessage.value = "Neural Image Synthesized!"
        }
    }

    // AI Chat
    fun sendChatMessage(prompt: String, model: String = "Gemini 3.5 Flash") {
        if (isUnsafe(prompt)) {
            _toastMessage.value = "⚠️ Safety Guard Active: NSFW or explicit queries are blocked."
            return
        }
        if (prompt.isBlank()) return
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            isFromUser = true,
            text = prompt,
            model = model
        )
        repository.addChatMessage(userMsg)

        viewModelScope.launch {
            _isChatGenerating.value = true
            val responseText = GeminiApiHelper.generateResponse(prompt)
            val aiMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                isFromUser = false,
                text = responseText,
                model = model
            )
            repository.addChatMessage(aiMsg)
            _isChatGenerating.value = false
        }
    }

    fun clearChat() {
        repository.clearChat()
        _toastMessage.value = "Chat buffer cleared."
    }

    // Friends Hub Direct Messaging
    fun openFriendChat(friend: FriendItem) {
        _activeChatFriend.value = friend
        _directMessages.value = listOf(
            DirectMessage("dm_1", "Hey there! Ready to collaborate on Lumina Movie Maker?", "10:30 AM", isMe = false),
            DirectMessage("dm_2", friend.lastMessage, friend.lastMessageTime, isMe = false)
        )
    }

    fun closeFriendChat() {
        _activeChatFriend.value = null
    }

    fun sendDirectMessage(text: String) {
        if (isUnsafe(text)) {
            _toastMessage.value = "⚠️ Safety Guard Active."
            return
        }
        if (text.isBlank()) return
        val newMsg = DirectMessage(
            id = UUID.randomUUID().toString(),
            text = text,
            time = "Just now",
            isMe = true
        )
        _directMessages.update { it + newMsg }

        viewModelScope.launch {
            delay(1200)
            val reply = DirectMessage(
                id = UUID.randomUUID().toString(),
                text = "Received loud and clear over the encrypted neural link! 👍",
                time = "Just now",
                isMe = false
            )
            _directMessages.update { it + reply }
        }
    }

    // Friends Hub Calling
    fun startCall(friend: FriendItem, isVideo: Boolean) {
        _activeCallFriend.value = friend
        _isVideoCall.value = isVideo
        _callSeconds.value = 0
        _isMuted.value = false
        _isSpeakerOn.value = true

        viewModelScope.launch {
            while (_activeCallFriend.value != null) {
                delay(1000)
                _callSeconds.update { it + 1 }
            }
        }
    }

    fun endCall() {
        _activeCallFriend.value = null
        _callSeconds.value = 0
        _toastMessage.value = "Call disconnected."
    }

    fun toggleMute() {
        _isMuted.update { !it }
    }

    fun toggleSpeaker() {
        _isSpeakerOn.update { !it }
    }

    fun saveStatus(statusId: String) {
        repository.saveStatus(statusId)
        _toastMessage.value = "Status media cached and saved to device gallery!"
    }

    fun openWhatsAppChat(context: Context, phoneNumber: String) {
        try {
            val url = "https://api.whatsapp.com/send?phone=${phoneNumber.replace(" ", "").replace("+", "")}&text=Hello%20from%20LUMINA%20AI%20Movie%20Maker!"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            _toastMessage.value = "Opening WhatsApp web link for $phoneNumber"
        }
    }
}
