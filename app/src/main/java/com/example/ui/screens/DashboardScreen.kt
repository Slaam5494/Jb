package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ModuleType
import com.example.ui.components.AdBannerSlot
import com.example.ui.components.LuminaHeader
import com.example.ui.components.TickerBar
import com.example.ui.theme.LuminaBackground
import com.example.ui.theme.LuminaBorder
import com.example.ui.theme.LuminaCardBg
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.LuminaGreen
import com.example.ui.theme.LuminaPink
import com.example.ui.theme.LuminaPurple
import com.example.ui.theme.LuminaTextPrimary
import com.example.ui.theme.LuminaTextSecondary

@Composable
fun DashboardScreen(
    coinBalance: Double,
    onOpenStore: () -> Unit,
    onModuleSelected: (ModuleType) -> Unit,
    onWatchAd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuminaBackground)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Top Header
        LuminaHeader(
            coinBalance = coinBalance,
            onOpenStore = onOpenStore
        )

        // Top Ad Banner Slot
        AdBannerSlot(
            title = "[ TOP SPONSOR BANNER • TAP TO EARN COINS ]",
            onClick = onWatchAd
        )

        // Ticker Bar
        TickerBar(
            message = "Welcome to LUMINA AI Movie Maker • 1 min to 80 min AI Movies & BGM Auto-Orchestration Available..."
        )

        // Main Content Area with 6 Modules
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "SYSTEM MODULES",
                color = LuminaCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.2.sp,
                modifier = Modifier.padding(start = 2.dp)
            )

            // Grid 2 columns x 3 rows
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Row 1: MOVIE MAKER + MEDIA GEN
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ModuleCard(
                        title = "MOVIE MAKER",
                        subtitle = "1 to 80 Min AI Movies & BGM",
                        icon = Icons.Default.Movie,
                        iconTint = LuminaPink,
                        imageRes = R.drawable.bg_video_gen,
                        testTag = "card_movie_maker",
                        onClick = { onModuleSelected(ModuleType.MOVIE_MAKER) },
                        modifier = Modifier.weight(1f)
                    )
                    ModuleCard(
                        title = "MEDIA GEN",
                        subtitle = "Video & Image Generator",
                        icon = Icons.Default.PermMedia,
                        iconTint = LuminaCyan,
                        imageRes = R.drawable.bg_image_gen,
                        testTag = "card_media_gen",
                        onClick = { onModuleSelected(ModuleType.MEDIA_GEN) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 2: AI CHAT + PROMPT STUDIO
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ModuleCard(
                        title = "AI CHAT",
                        subtitle = "Gemini Copy Interface",
                        icon = Icons.Default.Chat,
                        iconTint = LuminaCyan,
                        imageRes = R.drawable.bg_ai_chat,
                        testTag = "card_ai_chat",
                        onClick = { onModuleSelected(ModuleType.AI_CHAT) },
                        modifier = Modifier.weight(1f)
                    )
                    ModuleCard(
                        title = "PROMPT STUDIO",
                        subtitle = "Gemini Prompt Bar",
                        icon = Icons.Default.AutoAwesome,
                        iconTint = LuminaPurple,
                        imageRes = R.drawable.bg_prompt_studio,
                        testTag = "card_prompt_studio",
                        onClick = { onModuleSelected(ModuleType.PROMPT_STUDIO) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 3: FRIENDS HUB + SETTINGS
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ModuleCard(
                        title = "FRIENDS HUB",
                        subtitle = "WhatsApp & Calling (Monetized)",
                        icon = Icons.Default.Group,
                        iconTint = LuminaGreen,
                        imageRes = R.drawable.bg_friends_hub,
                        testTag = "card_friends_hub",
                        onClick = { onModuleSelected(ModuleType.FRIENDS_HUB) },
                        modifier = Modifier.weight(1f)
                    )
                    ModuleCard(
                        title = "SETTINGS",
                        subtitle = "App Feedback & WhatsApp",
                        icon = Icons.Default.Settings,
                        iconTint = LuminaCyan,
                        imageRes = null,
                        testTag = "card_settings",
                        onClick = { onModuleSelected(ModuleType.SETTINGS) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Bottom Ad Banner Slot
        AdBannerSlot(
            title = "[ BOTTOM SPONSOR BANNER • WATCH REWARDED AD ]",
            onClick = onWatchAd
        )
    }
}

@Composable
fun ModuleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    imageRes: Int?,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(LuminaCardBg)
            .border(BorderStroke(1.dp, LuminaBorder), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        // Background Image with cyber transparency
        if (imageRes != null) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.32f
            )
        }

        // Card Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )

            Column {
                Text(
                    text = title,
                    color = LuminaTextPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = subtitle,
                    color = LuminaTextSecondary,
                    fontSize = 7.5.sp,
                    maxLines = 1
                )
            }
        }
    }
}
