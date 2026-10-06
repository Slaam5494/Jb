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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ImageGenItem
import com.example.data.model.VideoGenItem
import com.example.ui.theme.LuminaBackground
import com.example.ui.theme.LuminaBorder
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.LuminaGold
import com.example.ui.theme.LuminaPink
import com.example.ui.theme.LuminaPurple
import com.example.ui.theme.LuminaSurface
import com.example.ui.theme.LuminaTextMuted
import com.example.ui.theme.LuminaTextPrimary
import com.example.ui.theme.LuminaTextSecondary

@Composable
fun MediaGenScreen(
    videoList: List<VideoGenItem>,
    imageList: List<ImageGenItem>,
    isVideoGenerating: Boolean,
    isImageGenerating: Boolean,
    userCoins: Double,
    onBack: () -> Unit,
    onGenerateVideo: (prompt: String, style: String, ratio: String, duration: Int, motion: String) -> Unit,
    onGenerateImage: (prompt: String, style: String, ratio: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf("video") } // "video" or "image"
    var promptText by remember { mutableStateOf("Futuristic cyber scene with vibrant neon lighting and 8K reflection dynamics") }

    val isGenerating = if (activeTab == "video") isVideoGenerating else isImageGenerating

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuminaBackground)
            .padding(10.dp)
    ) {
        // Header
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
                    modifier = Modifier.testTag("back_button_media")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LuminaCyan
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "MEDIA GENERATOR",
                        color = LuminaCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Video & Image Synthesis Matrix",
                        color = LuminaTextMuted,
                        fontSize = 8.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(LuminaGold.copy(alpha = 0.15f))
                    .border(1.dp, LuminaGold, RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${userCoins.toInt()} Coins",
                    color = LuminaGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Tab Selector Group
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Video Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (activeTab == "video") LuminaCyan else LuminaSurface)
                    .border(1.dp, if (activeTab == "video") LuminaCyan else LuminaBorder, RoundedCornerShape(6.dp))
                    .clickable { activeTab = "video" }
                    .padding(vertical = 8.dp)
                    .testTag("tab_video_gen"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        tint = if (activeTab == "video") Color.Black else LuminaCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "📹 VIDEO GEN",
                        color = if (activeTab == "video") Color.Black else Color.White,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Image Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (activeTab == "image") LuminaCyan else LuminaSurface)
                    .border(1.dp, if (activeTab == "image") LuminaCyan else LuminaBorder, RoundedCornerShape(6.dp))
                    .clickable { activeTab = "image" }
                    .padding(vertical = 8.dp)
                    .testTag("tab_image_gen"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = if (activeTab == "image") Color.Black else LuminaCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "🖼️ IMAGE GEN",
                        color = if (activeTab == "image") Color.Black else Color.White,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Prompt Card
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LuminaSurface.copy(alpha = 0.85f))
                        .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "MEDIA PROMPT",
                        color = LuminaCyan,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = promptText,
                        onValueChange = { promptText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .testTag("media_prompt_input"),
                        placeholder = {
                            Text(
                                "Type prompt for video or image generation...",
                                color = LuminaTextMuted,
                                fontSize = 9.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LuminaCyan,
                            unfocusedBorderColor = LuminaBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Generate Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    if (isGenerating) listOf(LuminaSurface, LuminaSurface)
                                    else listOf(LuminaCyan, LuminaPink)
                                )
                            )
                            .clickable(enabled = !isGenerating) {
                                if (activeTab == "video") {
                                    onGenerateVideo(promptText, "Cinematic Matrix", "16:9", 10, "Orbit 360")
                                } else {
                                    onGenerateImage(promptText, "9D Cyberpunk", "1:1")
                                }
                            }
                            .padding(vertical = 10.dp)
                            .testTag("generate_media_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isGenerating) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = LuminaCyan,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "SYNTHESIZING ${activeTab.uppercase()} MATRIX...",
                                    color = LuminaCyan,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "✨ GENERATE MEDIA (${if (activeTab == "video") "2 COINS" else "1 COIN"})",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }

            // Results Section
            if (activeTab == "video") {
                item {
                    Text(
                        text = "VIDEO MEDIA ARCHIVE (${videoList.size})",
                        color = LuminaCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
                items(videoList) { video ->
                    VideoPlayerCard(video = video)
                }
            } else {
                item {
                    Text(
                        text = "IMAGE MEDIA ARCHIVE (${imageList.size})",
                        color = LuminaCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
                items(imageList) { img ->
                    ImageArtworkCard(image = img)
                }
            }
        }
    }
}
