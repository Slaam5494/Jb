package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.VideoGenItem
import com.example.ui.theme.LuminaBackground
import com.example.ui.theme.LuminaBorder
import com.example.ui.theme.LuminaCardBg
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.LuminaGold
import com.example.ui.theme.LuminaPink
import com.example.ui.theme.LuminaPurple
import com.example.ui.theme.LuminaSurface
import com.example.ui.theme.LuminaTextMuted
import com.example.ui.theme.LuminaTextPrimary
import com.example.ui.theme.LuminaTextSecondary

@Composable
fun VideoGenScreen(
    videoList: List<VideoGenItem>,
    isGenerating: Boolean,
    progress: Float,
    userCoins: Double,
    onBack: () -> Unit,
    onGenerate: (prompt: String, style: String, ratio: String, duration: Int, motion: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var prompt by remember { mutableStateOf("Futuristic neon hypercar driving through a rainy holographic metropolis") }
    var selectedStyle by remember { mutableStateOf("Cinematic Matrix") }
    var selectedRatio by remember { mutableStateOf("16:9") }
    var selectedDuration by remember { mutableIntStateOf(10) }
    var selectedMotion by remember { mutableStateOf("Orbit 360") }

    val styles = listOf("Cinematic Matrix", "Hologram 9D", "Cyberpunk", "Anime 4K", "Unreal 5")
    val ratios = listOf("16:9", "9:16", "1:1")
    val motions = listOf("Orbit 360", "Dolly Zoom", "Drone Sweep", "Pan Left/Right")

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
                    modifier = Modifier.testTag("back_button_video")
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
                        text = "VIDEO GEN",
                        color = LuminaCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Compact FX Screen • 9D Engine",
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

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Prompt input section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LuminaSurface.copy(alpha = 0.8f))
                        .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "VIDEO PROMPT",
                        color = LuminaCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .testTag("video_prompt_input"),
                        placeholder = {
                            Text(
                                "Describe camera movements, lighting, and visual dynamics...",
                                color = LuminaTextMuted,
                                fontSize = 10.sp
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

                    // Quick suggestion pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "Cyberpunk Rain",
                            "Quantum Singularity",
                            "Hologram Avatar",
                            "Orbital Space Station"
                        ).forEach { suggestion ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(LuminaCyan.copy(alpha = 0.1f))
                                    .border(1.dp, LuminaBorder, RoundedCornerShape(6.dp))
                                    .clickable { prompt = "$suggestion with volumetric lighting and dynamic motion" }
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = suggestion,
                                    color = LuminaCyan,
                                    fontSize = 8.sp
                                )
                            }
                        }
                    }

                    // Style selector
                    Text(
                        text = "FX MOTION STYLE",
                        color = LuminaTextSecondary,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        styles.forEach { style ->
                            val isSelected = selectedStyle == style
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) LuminaCyan else LuminaSurface)
                                    .border(1.dp, if (isSelected) LuminaCyan else LuminaBorder, RoundedCornerShape(6.dp))
                                    .clickable { selectedStyle = style }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = style,
                                    color = if (isSelected) Color.Black else LuminaTextPrimary,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Aspect ratio & Duration
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Ratio
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ASPECT RATIO",
                                color = LuminaTextSecondary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                ratios.forEach { r ->
                                    val isSelected = selectedRatio == r
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isSelected) LuminaPurple else LuminaSurface)
                                            .border(1.dp, if (isSelected) LuminaPurple else LuminaBorder, RoundedCornerShape(4.dp))
                                            .clickable { selectedRatio = r }
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = r,
                                            color = Color.White,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // Duration
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DURATION",
                                color = LuminaTextSecondary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                listOf(5, 10, 15).forEach { d ->
                                    val isSelected = selectedDuration == d
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isSelected) LuminaPink else LuminaSurface)
                                            .border(1.dp, if (isSelected) LuminaPink else LuminaBorder, RoundedCornerShape(4.dp))
                                            .clickable { selectedDuration = d }
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "${d}s",
                                            color = Color.White,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Camera motion
                    Text(
                        text = "CAMERA TRAJECTORY",
                        color = LuminaTextSecondary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        motions.forEach { m ->
                            val isSelected = selectedMotion == m
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSelected) LuminaCyan.copy(alpha = 0.2f) else LuminaSurface)
                                    .border(1.dp, if (isSelected) LuminaCyan else LuminaBorder, RoundedCornerShape(4.dp))
                                    .clickable { selectedMotion = m }
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = m,
                                    color = if (isSelected) LuminaCyan else LuminaTextSecondary,
                                    fontSize = 8.sp
                                )
                            }
                        }
                    }

                    // Generate Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    if (isGenerating) listOf(LuminaSurface, LuminaSurface)
                                    else listOf(LuminaCyan, LuminaPurple)
                                )
                            )
                            .clickable(enabled = !isGenerating) {
                                onGenerate(prompt, selectedStyle, selectedRatio, selectedDuration, selectedMotion)
                            }
                            .padding(vertical = 10.dp)
                            .testTag("generate_video_button"),
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
                                    text = "SYNTHESIZING 9D FRAMES (${(progress * 100).toInt()}%)...",
                                    color = LuminaCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "RENDER VIDEO (COST: 10 COINS)",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }

                    if (isGenerating) {
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = LuminaCyan,
                            trackColor = LuminaSurface
                        )
                    }
                }
            }

            // Generated Videos Section
            item {
                Text(
                    text = "RENDERED 9D SEQUENCES (${videoList.size})",
                    color = LuminaCyan,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }

            items(videoList) { video ->
                VideoPlayerCard(video = video)
            }
        }
    }
}

@Composable
fun VideoPlayerCard(
    video: VideoGenItem,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var currentScrub by remember { mutableFloatStateOf(0.4f) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(LuminaCardBg)
            .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Video Preview Screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_video_gen),
                contentDescription = "Video Preview",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = if (isPlaying) 0.95f else 0.7f
            )

            // Play/Pause overlay
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .border(1.dp, LuminaCyan, CircleShape)
                    .clickable { isPlaying = !isPlaying },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = LuminaCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Top Badges
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .border(1.dp, LuminaCyan, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = video.style,
                        color = LuminaCyan,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .border(1.dp, LuminaPurple, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${video.durationSec}s • ${video.aspectRatio}",
                        color = LuminaPurple,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Timeline Scrubber
        Slider(
            value = currentScrub,
            onValueChange = { currentScrub = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp),
            colors = SliderDefaults.colors(
                thumbColor = LuminaCyan,
                activeTrackColor = LuminaCyan,
                inactiveTrackColor = LuminaSurface
            )
        )

        // Metadata & Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = video.prompt,
                    color = Color.White,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
                Text(
                    text = "Motion: ${video.cameraMotion} • Spatial 9D Keyframe Lock",
                    color = LuminaTextMuted,
                    fontSize = 7.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = { /* Simulated download */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = LuminaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = { /* Simulated share */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = LuminaPurple,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
