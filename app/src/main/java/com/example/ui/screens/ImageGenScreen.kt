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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Palette
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ImageGenItem
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
fun ImageGenScreen(
    imageList: List<ImageGenItem>,
    isGenerating: Boolean,
    userCoins: Double,
    onBack: () -> Unit,
    onGenerate: (prompt: String, style: String, ratio: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var prompt by remember { mutableStateOf("Cybernetic avatar in quantum cyberspace with luminous neon infinity halo") }
    var selectedStyle by remember { mutableStateOf("9D Cyberpunk") }
    var selectedRatio by remember { mutableStateOf("1:1") }

    val styles = listOf("9D Cyberpunk", "Neon Hologram", "Photoreal AR", "Synthwave", "Sci-Fi Anime")
    val ratios = listOf("1:1", "16:9", "9:16")

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
                    modifier = Modifier.testTag("back_button_image")
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
                        text = "IMAGE GEN",
                        color = LuminaCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Compact Studio • Neural Diffusion",
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
            // Creation Box
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "IMAGE PROMPT",
                            color = LuminaCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(LuminaPurple.copy(alpha = 0.2f))
                                .border(1.dp, LuminaPurple, RoundedCornerShape(6.dp))
                                .clickable {
                                    prompt = "$prompt, 8k resolution, octane render, volumetric cyan and magenta glow, hyper-detailed, masterpiece"
                                }
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = LuminaPurple,
                                modifier = Modifier.size(10.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ENHANCE 9D",
                                color = LuminaPurple,
                                fontSize = 7.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .testTag("image_prompt_input"),
                        placeholder = {
                            Text(
                                "Describe characters, environment, neon lighting and composition...",
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

                    // Styles
                    Text(
                        text = "DIFFUSION STYLE",
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

                    // Ratio
                    Text(
                        text = "ASPECT RATIO",
                        color = LuminaTextSecondary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        ratios.forEach { r ->
                            val isSelected = selectedRatio == r
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSelected) LuminaPurple else LuminaSurface)
                                    .border(1.dp, if (isSelected) LuminaPurple else LuminaBorder, RoundedCornerShape(4.dp))
                                    .clickable { selectedRatio = r }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
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
                                onGenerate(prompt, selectedStyle, selectedRatio)
                            }
                            .padding(vertical = 10.dp)
                            .testTag("generate_image_button"),
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
                                    text = "SYNTHESIZING NEURAL LATENT MATRIX...",
                                    color = LuminaCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "SYNTHESIZE IMAGE (COST: 5 COINS)",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }

            // Gallery
            item {
                Text(
                    text = "NEURAL GALLERY (${imageList.size})",
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

@Composable
fun ImageArtworkCard(
    image: ImageGenItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(LuminaCardBg)
            .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Image Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            val resId = image.drawableRes ?: R.drawable.bg_image_gen
            Image(
                painter = painterResource(id = resId),
                contentDescription = "Synthesized Art",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

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
                        text = image.style,
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
                        text = image.aspectRatio,
                        color = LuminaPurple,
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Prompt & Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = image.prompt,
                color = Color.White,
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = { /* Simulated download */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Save",
                        tint = LuminaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = { /* Simulated copy */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Prompt",
                        tint = LuminaPurple,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
