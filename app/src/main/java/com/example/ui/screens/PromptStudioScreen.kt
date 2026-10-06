package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ModuleType
import com.example.data.model.PromptTemplate
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
fun PromptStudioScreen(
    templates: List<PromptTemplate>,
    onBack: () -> Unit,
    onNavigateTo: (ModuleType) -> Unit,
    onCopyPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var rawDraft by remember { mutableStateOf("Futuristic cyber motorcycle racing through holographic tunnel") }
    var optimizedPrompt by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var temperature by remember { mutableFloatStateOf(0.7f) }

    val categories = listOf("All", "Video & 3D", "Image Gen", "Code & Tech", "Marketing")
    val clipboardManager = LocalClipboardManager.current

    val filteredTemplates = remember(selectedCategory, templates) {
        if (selectedCategory == "All") templates
        else templates.filter { it.category == selectedCategory }
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
                    modifier = Modifier.testTag("back_button_prompt")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LuminaPurple
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "PROMPT STUDIO",
                        color = LuminaPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Gemini Prompt Bar • Quantum Refiner",
                        color = LuminaTextMuted,
                        fontSize = 8.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuminaPurple.copy(alpha = 0.2f))
                    .border(1.dp, LuminaPurple, RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "EST. TOKENS: ~${(rawDraft.length / 4).coerceAtLeast(12)}",
                    color = LuminaPurple,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Interactive Optimizer
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
                        text = "NEURAL PROMPT SYNTHESIZER",
                        color = LuminaCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = rawDraft,
                        onValueChange = { rawDraft = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .testTag("prompt_studio_input"),
                        placeholder = {
                            Text("Enter prompt concepts to expand into high-fidelity directives...", color = LuminaTextMuted, fontSize = 9.sp)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LuminaPurple,
                            unfocusedBorderColor = LuminaBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Temperature Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CREATIVITY / TEMP: ${String.format("%.1f", temperature)}",
                            color = LuminaTextSecondary,
                            fontSize = 7.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Slider(
                            value = temperature,
                            onValueChange = { temperature = it },
                            valueRange = 0.1f..1.0f,
                            modifier = Modifier.width(130.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = LuminaPurple,
                                activeTrackColor = LuminaPurple,
                                inactiveTrackColor = LuminaSurface
                            )
                        )
                    }

                    // Optimize Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(listOf(LuminaPurple, LuminaPink))
                            )
                            .clickable {
                                optimizedPrompt = "$rawDraft, masterwork, 8K ultra high fidelity, hyper-detailed octane render, volumetric lighting, cyan and magenta atmospheric refraction, anamorphic cinematic lens, 35mm photograph, flawless composition, trending on ArtStation --ar 16:9 --v 6.0"
                            }
                            .padding(vertical = 9.dp)
                            .testTag("optimize_prompt_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "EXPAND TO 9D MASTER PROMPT",
                                color = Color.White,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // Result Preview if optimized
                    if (optimizedPrompt.isNotBlank()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(LuminaBackground)
                                .border(1.dp, LuminaCyan, RoundedCornerShape(6.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "OPTIMIZED RESULT",
                                    color = LuminaCyan,
                                    fontSize = 7.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    IconButton(
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(optimizedPrompt))
                                            onCopyPrompt("Copied to clipboard!")
                                        },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = LuminaCyan,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = optimizedPrompt,
                                color = Color.White,
                                fontSize = 8.sp,
                                lineHeight = 12.sp
                            )
                        }
                    }
                }
            }

            // Category Filter
            item {
                Text(
                    text = "CURATED PROMPT TEMPLATES",
                    color = LuminaCyan,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) LuminaPurple else LuminaSurface)
                            .border(1.dp, if (isSelected) LuminaPurple else LuminaBorder, RoundedCornerShape(6.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else LuminaTextSecondary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Template Cards
            items(filteredTemplates) { template ->
                TemplateCard(
                    template = template,
                    onUse = {
                        rawDraft = template.template
                        clipboardManager.setText(AnnotatedString(template.template))
                        onCopyPrompt("Loaded template: ${template.title}")
                    }
                )
            }
        }
    }
}

@Composable
fun TemplateCard(
    template: PromptTemplate,
    onUse: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LuminaCardBg)
            .border(1.dp, LuminaBorder, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = template.title,
                color = LuminaTextPrimary,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LuminaPurple.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = template.category,
                    color = LuminaPurple,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = template.description,
            color = LuminaTextSecondary,
            fontSize = 7.5.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(LuminaSurface)
                .padding(6.dp)
        ) {
            Text(
                text = template.template,
                color = LuminaCyan,
                fontSize = 7.5.sp,
                maxLines = 2
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LuminaCyan.copy(alpha = 0.15f))
                    .border(1.dp, LuminaCyan, RoundedCornerShape(4.dp))
                    .clickable { onUse() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "USE TEMPLATE",
                    color = LuminaCyan,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
