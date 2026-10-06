package com.example.ui.screens

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
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
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
import com.example.data.model.MovieProject
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
fun MovieMakerScreen(
    movieProjects: List<MovieProject>,
    isGenerating: Boolean,
    progress: Float,
    userCoins: Double,
    onBack: () -> Unit,
    onBuildMovie: (script: String, duration: Int, bgm: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var scriptText by remember { mutableStateOf("A high-octane cyberpunk space armada battle over the neon rings of Saturn, culminating in an emotional reunion.") }
    var selectedDuration by remember { mutableIntStateOf(15) }
    var selectedBgm by remember { mutableStateOf("Cinematic Action BGM") }

    val durationOptions = listOf(
        Pair(1, "1 Min (Shorts)"),
        Pair(5, "5 Min (Scene)"),
        Pair(15, "15 Min (Clip)"),
        Pair(30, "30 Min (Drama)"),
        Pair(80, "80 Min (Full Feature)")
    )

    val bgmOptions = listOf(
        "Auto (AI Detects Scene Mood)",
        "Classic / Orchestral",
        "Cinematic Action BGM",
        "Jazz / Smooth",
        "Rock / High Energy"
    )

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
                    modifier = Modifier.testTag("back_button_movie")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LuminaPink
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "9D MOVIE MAKER",
                        color = LuminaPink,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "1 to 80 Min AI Movies & BGM Auto-Orchestration",
                        color = LuminaTextMuted,
                        fontSize = 7.5.sp
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
            // Movie Setup Box
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LuminaSurface.copy(alpha = 0.85f))
                        .border(1.dp, LuminaPink.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "MOVIE STORYLINE SCRIPT",
                        color = LuminaCyan,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = scriptText,
                        onValueChange = { scriptText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .testTag("movie_script_input"),
                        placeholder = {
                            Text(
                                "Describe storyline (e.g., A sci-fi action space battle with emotional scenes)...",
                                color = LuminaTextMuted,
                                fontSize = 9.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LuminaPink,
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
                            "Space Armada Battle",
                            "Cyberpunk Detective",
                            "Quantum Time Heist",
                            "Emotional AI Journey"
                        ).forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(LuminaPink.copy(alpha = 0.15f))
                                    .border(1.dp, LuminaPink.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                    .clickable { scriptText = "A cinematic $tag set in a neon metropolis with orchestral crescendo." }
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(tag, color = LuminaPink, fontSize = 7.5.sp)
                            }
                        }
                    }

                    // Select Duration
                    Text(
                        text = "SELECT DURATION",
                        color = LuminaTextSecondary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        durationOptions.forEach { (mins, label) ->
                            val isSelected = selectedDuration == mins
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) LuminaPink else LuminaSurface)
                                    .border(1.dp, if (isSelected) LuminaPink else LuminaBorder, RoundedCornerShape(6.dp))
                                    .clickable { selectedDuration = mins }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Timer,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else LuminaTextSecondary,
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = label,
                                        color = if (isSelected) Color.White else LuminaTextSecondary,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Auto Background Music (Equalizer)
                    Text(
                        text = "AUTO BACKGROUND MUSIC (EQUALIZER)",
                        color = LuminaTextSecondary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        bgmOptions.forEach { bgm ->
                            val isSelected = selectedBgm == bgm
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) LuminaCyan else LuminaSurface)
                                    .border(1.dp, if (isSelected) LuminaCyan else LuminaBorder, RoundedCornerShape(6.dp))
                                    .clickable { selectedBgm = bgm }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.MusicNote,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.Black else LuminaCyan,
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = bgm,
                                        color = if (isSelected) Color.Black else LuminaTextPrimary,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Build Button
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
                                onBuildMovie(scriptText, selectedDuration, selectedBgm)
                            }
                            .padding(vertical = 10.dp)
                            .testTag("build_movie_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isGenerating) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = LuminaPink,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "ORCHESTRATING SCENE SEQUENCE & BGM (${(progress * 100).toInt()}%)...",
                                    color = LuminaPink,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Movie,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "🎬 BUILD AI MOVIE ($selectedDuration MIN)",
                                    color = Color.White,
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
                            color = LuminaPink,
                            trackColor = LuminaSurface
                        )
                    }
                }
            }

            // Movie Projects List
            item {
                Text(
                    text = "ORCHESTRATED MOVIE PROJECTS (${movieProjects.size})",
                    color = LuminaPink,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }

            items(movieProjects) { project ->
                MovieProjectCard(project = project)
            }
        }
    }
}

@Composable
fun MovieProjectCard(
    project: MovieProject,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var scrubPosition by remember { mutableFloatStateOf(0.35f) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(LuminaCardBg)
            .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Video Preview Canvas
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
                contentDescription = "Movie Preview",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = if (isPlaying) 0.95f else 0.7f
            )

            // Play/Pause button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.65f))
                    .border(1.5.dp, LuminaPink, CircleShape)
                    .clickable { isPlaying = !isPlaying },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = LuminaPink,
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
                        .background(Color.Black.copy(alpha = 0.75f))
                        .border(1.dp, LuminaPink, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${project.durationMinutes} MIN FEATURE",
                        color = LuminaPink,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.75f))
                        .border(1.dp, LuminaCyan, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "BGM: ${project.bgmStyle}",
                        color = LuminaCyan,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Timeline Slider
        Slider(
            value = scrubPosition,
            onValueChange = { scrubPosition = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp),
            colors = SliderDefaults.colors(
                thumbColor = LuminaPink,
                activeTrackColor = LuminaPink,
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
                    text = project.title,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = project.script,
                    color = LuminaTextSecondary,
                    fontSize = 7.5.sp,
                    maxLines = 2
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = { /* Simulated download */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download Movie",
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
                        tint = LuminaPink,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
