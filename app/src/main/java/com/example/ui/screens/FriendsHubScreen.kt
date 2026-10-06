package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.DirectMessage
import com.example.data.model.FriendItem
import com.example.data.model.StatusItem
import com.example.ui.theme.LuminaBackground
import com.example.ui.theme.LuminaBorder
import com.example.ui.theme.LuminaCardBg
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.LuminaGold
import com.example.ui.theme.LuminaGreen
import com.example.ui.theme.LuminaPink
import com.example.ui.theme.LuminaPurple
import com.example.ui.theme.LuminaRed
import com.example.ui.theme.LuminaSurface
import com.example.ui.theme.LuminaTextMuted
import com.example.ui.theme.LuminaTextPrimary
import com.example.ui.theme.LuminaTextSecondary

@Composable
fun FriendsHubScreen(
    friends: List<FriendItem>,
    statuses: List<StatusItem>,
    activeCallFriend: FriendItem?,
    isVideoCall: Boolean,
    callSeconds: Int,
    isMuted: Boolean,
    isSpeakerOn: Boolean,
    activeChatFriend: FriendItem?,
    directMessages: List<DirectMessage>,
    onBack: () -> Unit,
    onOpenStore: () -> Unit,
    onStartCall: (FriendItem, Boolean) -> Unit,
    onEndCall: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleSpeaker: () -> Unit,
    onOpenChat: (FriendItem) -> Unit,
    onCloseChat: () -> Unit,
    onSendDirectMessage: (String) -> Unit,
    onSaveStatus: (String) -> Unit,
    onOpenWhatsApp: (Context, String) -> Unit,
    onWatchAd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0: CHATS, 1: STATUS SAVER, 2: VIP THEMES
    val tabs = listOf("CHATS", "STATUS SAVER", "VIP THEMES")

    // If an active call is ongoing, show the Active Call Screen
    if (activeCallFriend != null) {
        ActiveCallScreen(
            friend = activeCallFriend,
            isVideo = isVideoCall,
            durationSeconds = callSeconds,
            isMuted = isMuted,
            isSpeakerOn = isSpeakerOn,
            onToggleMute = onToggleMute,
            onToggleSpeaker = onToggleSpeaker,
            onEndCall = onEndCall
        )
        return
    }

    // If a direct chat is active, show the Direct Chat Overlay
    if (activeChatFriend != null) {
        FriendChatOverlay(
            friend = activeChatFriend,
            messages = directMessages,
            onClose = onCloseChat,
            onSendMessage = onSendDirectMessage,
            onStartCall = { isVid -> onStartCall(activeChatFriend, isVid) },
            onOpenWhatsApp = { onOpenWhatsApp(context, activeChatFriend.phoneNumber) }
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuminaBackground)
            .padding(10.dp)
    ) {
        // Screen Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("back_button_friends")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LuminaGreen
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "FRIENDS HUB",
                        color = LuminaGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "WhatsApp & HD Encrypted Calling (Monetized)",
                        color = LuminaTextMuted,
                        fontSize = 8.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuminaGreen.copy(alpha = 0.15f))
                    .border(1.dp, LuminaGreen, RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "AR SECURE",
                    color = LuminaGreen,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTab == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) LuminaGreen else LuminaSurface)
                        .border(1.dp, if (isSelected) LuminaGreen else LuminaBorder, RoundedCornerShape(6.dp))
                        .clickable { selectedTab = index }
                        .padding(vertical = 6.dp)
                        .testTag("friends_tab_$index"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = if (isSelected) Color.Black else Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> ChatsTabContent(
                friends = friends,
                onOpenChat = onOpenChat,
                onStartCall = onStartCall,
                onOpenWhatsApp = { phone -> onOpenWhatsApp(context, phone) },
                onOpenStore = onOpenStore,
                onWatchAd = onWatchAd
            )
            1 -> StatusSaverTabContent(
                statuses = statuses,
                onSaveStatus = onSaveStatus,
                onWatchAd = onWatchAd
            )
            2 -> VipThemesTabContent(
                onOpenStore = onOpenStore
            )
        }
    }
}

@Composable
fun ChatsTabContent(
    friends: List<FriendItem>,
    onOpenChat: (FriendItem) -> Unit,
    onStartCall: (FriendItem, Boolean) -> Unit,
    onOpenWhatsApp: (String) -> Unit,
    onOpenStore: () -> Unit,
    onWatchAd: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Monetize VIP Banner
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuminaGold.copy(alpha = 0.1f))
                    .border(1.dp, LuminaGold, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "★ UNLOCK VIP FRIENDS HUB & 9D VIDEO CALLS ★",
                    color = LuminaGold,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Brush.horizontalGradient(listOf(LuminaGold, Color(0xFFFF8C00))))
                            .clickable { onOpenStore() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "GET VIP PASS ($0.99)",
                            color = Color.Black,
                            fontSize = 7.5.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(LuminaCyan.copy(alpha = 0.15f))
                            .border(1.dp, LuminaCyan, RoundedCornerShape(12.dp))
                            .clickable { onWatchAd() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "WATCH AD (+10 COINS)",
                            color = LuminaCyan,
                            fontSize = 7.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Friends List
        items(friends) { friend ->
            FriendListItem(
                friend = friend,
                onOpenChat = { onOpenChat(friend) },
                onVoiceCall = { onStartCall(friend, false) },
                onVideoCall = { onStartCall(friend, true) },
                onWhatsApp = { onOpenWhatsApp(friend.phoneNumber) }
            )
        }
    }
}

@Composable
fun FriendListItem(
    friend: FriendItem,
    onOpenChat: () -> Unit,
    onVoiceCall: () -> Unit,
    onVideoCall: () -> Unit,
    onWhatsApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LuminaCardBg)
            .border(1.dp, LuminaBorder, RoundedCornerShape(8.dp))
            .clickable { onOpenChat() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with Online dot
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = friend.avatarUrl,
                contentDescription = friend.name,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, LuminaGreen, CircleShape),
                contentScale = ContentScale.Crop
            )
            if (friend.isOnline) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(LuminaGreen)
                        .border(1.dp, Color.Black, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = friend.name,
                color = LuminaTextPrimary,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = friend.status,
                color = if (friend.isOnline) LuminaGreen else LuminaTextMuted,
                fontSize = 7.5.sp
            )
            Text(
                text = friend.lastMessage,
                color = LuminaTextSecondary,
                fontSize = 7.sp,
                maxLines = 1
            )
        }

        // Actions
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            // Voice Call
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(LuminaSurface)
                    .border(1.dp, LuminaCyan, CircleShape)
                    .clickable { onVoiceCall() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Voice Call",
                    tint = LuminaCyan,
                    modifier = Modifier.size(12.dp)
                )
            }

            // Video Call
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(LuminaSurface)
                    .border(1.dp, LuminaGreen, CircleShape)
                    .clickable { onVideoCall() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = "Video Call",
                    tint = LuminaGreen,
                    modifier = Modifier.size(12.dp)
                )
            }

            // WhatsApp link
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(LuminaGreen.copy(alpha = 0.2f))
                    .border(1.dp, LuminaGreen, CircleShape)
                    .clickable { onWhatsApp() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "WhatsApp",
                    tint = LuminaGreen,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
fun StatusSaverTabContent(
    statuses: List<StatusItem>,
    onSaveStatus: (String) -> Unit,
    onWatchAd: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuminaCyan.copy(alpha = 0.1f))
                    .border(1.dp, LuminaCyan, RoundedCornerShape(8.dp))
                    .clickable { onWatchAd() }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WATCH 1 SPONSOR AD TO UNLOCK ULTRA 4K STATUS SAVER",
                    color = LuminaCyan,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(statuses) { status ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuminaCardBg)
                    .border(1.dp, LuminaBorder, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = status.avatarUrl,
                    contentDescription = status.friendName,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, LuminaGreen, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = status.friendName,
                        color = LuminaTextPrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = status.caption,
                        color = LuminaTextSecondary,
                        fontSize = 7.5.sp,
                        maxLines = 2
                    )
                    Text(
                        text = status.timeAgo,
                        color = LuminaTextMuted,
                        fontSize = 6.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (status.isSaved) LuminaGreen else LuminaCyan.copy(alpha = 0.2f))
                        .border(1.dp, if (status.isSaved) LuminaGreen else LuminaCyan, RoundedCornerShape(6.dp))
                        .clickable { onSaveStatus(status.id) }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (status.isSaved) Icons.Default.Check else Icons.Default.Download,
                            contentDescription = null,
                            tint = if (status.isSaved) Color.Black else LuminaCyan,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (status.isSaved) "SAVED" else "SAVE",
                            color = if (status.isSaved) Color.Black else LuminaCyan,
                            fontSize = 7.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VipThemesTabContent(
    onOpenStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themes = listOf(
        Triple("Quantum Cyan Neon", LuminaCyan, "Default Active"),
        Triple("Golden Cyber Titan", LuminaGold, "Requires VIP"),
        Triple("Matrix Emerald 9D", LuminaGreen, "Requires VIP"),
        Triple("Deep Ultraviolet AR", LuminaPurple, "Requires VIP")
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        themes.forEach { (name, color, status) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuminaCardBg)
                    .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .clickable { onOpenStore() }
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(color.copy(alpha = 0.2f))
                        .border(1.dp, color, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = status,
                        color = color,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ActiveCallScreen(
    friend: FriendItem,
    isVideo: Boolean,
    durationSeconds: Int,
    isMuted: Boolean,
    isSpeakerOn: Boolean,
    onToggleMute: () -> Unit,
    onToggleSpeaker: () -> Unit,
    onEndCall: () -> Unit
) {
    val minutes = durationSeconds / 60
    val seconds = durationSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020712))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Holographic simulated background if video call
        if (isVideo) {
            Image(
                painter = painterResource(id = R.drawable.bg_video_gen),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.45f
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // Call status tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(LuminaGreen.copy(alpha = 0.2f))
                    .border(1.dp, LuminaGreen, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (isVideo) "9D ENCRYPTED VIDEO CALL" else "9D ENCRYPTED AUDIO CALL",
                    color = LuminaGreen,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Friend Avatar and Name
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(3.dp, LuminaGreen, CircleShape)
                        .padding(3.dp)
                ) {
                    AsyncImage(
                        model = friend.avatarUrl,
                        contentDescription = friend.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = friend.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timeFormatted,
                    color = LuminaCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Call Controls Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mute
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (isMuted) LuminaRed else LuminaSurface)
                        .border(1.dp, LuminaCyan, CircleShape)
                        .clickable { onToggleMute() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = "Mute",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // End Call (Big Red)
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(LuminaRed)
                        .clickable { onEndCall() }
                        .testTag("end_call_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Speaker
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (isSpeakerOn) LuminaCyan.copy(alpha = 0.2f) else LuminaSurface)
                        .border(1.dp, LuminaCyan, CircleShape)
                        .clickable { onToggleSpeaker() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Speaker",
                        tint = LuminaCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FriendChatOverlay(
    friend: FriendItem,
    messages: List<DirectMessage>,
    onClose: () -> Unit,
    onSendMessage: (String) -> Unit,
    onStartCall: (Boolean) -> Unit,
    onOpenWhatsApp: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuminaBackground)
            .padding(10.dp)
    ) {
        // Chat Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LuminaGreen
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                AsyncImage(
                    model = friend.avatarUrl,
                    contentDescription = friend.name,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .border(1.dp, LuminaGreen, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = friend.name,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = friend.status,
                        color = LuminaGreen,
                        fontSize = 7.sp
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = { onStartCall(false) },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        tint = LuminaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = { onStartCall(true) },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = "Video Call",
                        tint = LuminaGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = onOpenWhatsApp,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = "WhatsApp",
                        tint = LuminaGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(messages) { msg ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (msg.isMe) Alignment.End else Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (msg.isMe) LuminaGreen.copy(alpha = 0.25f)
                                else LuminaSurface
                            )
                            .border(
                                1.dp,
                                if (msg.isMe) LuminaGreen else LuminaBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = msg.text,
                                color = Color.White,
                                fontSize = 8.5.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = msg.time,
                                color = LuminaTextMuted,
                                fontSize = 6.5.sp,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }

        // Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                placeholder = {
                    Text("Type encrypted direct message...", color = LuminaTextMuted, fontSize = 8.5.sp)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LuminaGreen,
                    unfocusedBorderColor = LuminaBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuminaGreen)
                    .clickable(enabled = text.isNotBlank()) {
                        val t = text
                        text = ""
                        onSendMessage(t)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
