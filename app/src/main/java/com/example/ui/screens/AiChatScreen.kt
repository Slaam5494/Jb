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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ChatMessage
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
fun AiChatScreen(
    messages: List<ChatMessage>,
    isGenerating: Boolean,
    onBack: () -> Unit,
    onSendMessage: (String, String) -> Unit,
    onClearChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var selectedModel by remember { mutableStateOf("Gemini 3.5 Flash") }
    val models = listOf("Gemini 3.5 Flash", "Gemini 3.1 Pro", "Neural 9D Turbo")

    val listState = rememberLazyListState()
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

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
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("back_button_chat")
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
                        text = "AI CHAT",
                        color = LuminaCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Gemini Copy Interface • 9D Neural Core",
                        color = LuminaTextMuted,
                        fontSize = 8.sp
                    )
                }
            }

            IconButton(
                onClick = onClearChat,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteSweep,
                    contentDescription = "Clear Chat",
                    tint = LuminaPink,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Model selector tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            models.forEach { model ->
                val isSelected = selectedModel == model
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) LuminaCyan.copy(alpha = 0.2f) else LuminaSurface)
                        .border(1.dp, if (isSelected) LuminaCyan else LuminaBorder, RoundedCornerShape(6.dp))
                        .clickable { selectedModel = model }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = model,
                        color = if (isSelected) LuminaCyan else LuminaTextSecondary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Quick prompts
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                "Explain quantum neural networks",
                "Write an AR video prompt",
                "Generate Kotlin Compose UI code",
                "Optimize prompt for Midjourney"
            ).forEach { quickPrompt ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuminaPurple.copy(alpha = 0.12f))
                        .border(1.dp, LuminaPurple.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .clickable { onSendMessage(quickPrompt, selectedModel) }
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = quickPrompt,
                        color = LuminaPurple,
                        fontSize = 7.5.sp
                    )
                }
            }
        }

        // Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                ChatBubble(
                    message = msg,
                    onCopy = {
                        clipboardManager.setText(AnnotatedString(msg.text))
                    }
                )
            }

            if (isGenerating) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(14.dp),
                            color = LuminaCyan,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "LUMINA 9D synthesizing response...",
                            color = LuminaCyan,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Input Field Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("chat_input_field"),
                placeholder = {
                    Text("Transmit prompt to Neural Core...", color = LuminaTextMuted, fontSize = 9.sp)
                },
                trailingIcon = {
                    IconButton(
                        onClick = { inputText = "Synthesize an AR holographic interface layout" },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice Prompt",
                            tint = LuminaCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LuminaCyan,
                    unfocusedBorderColor = LuminaBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                maxLines = 2
            )

            Spacer(modifier = Modifier.width(6.dp))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.horizontalGradient(listOf(LuminaCyan, LuminaPurple))
                    )
                    .clickable(enabled = inputText.isNotBlank() && !isGenerating) {
                        val text = inputText
                        inputText = ""
                        onSendMessage(text, selectedModel)
                    }
                    .testTag("chat_send_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isUser = message.isFromUser

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(if (isUser) 0.82f else 0.92f)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isUser) LuminaCyan.copy(alpha = 0.18f)
                    else LuminaSurface.copy(alpha = 0.9f)
                )
                .border(
                    BorderStroke(
                        1.dp,
                        if (isUser) LuminaCyan.copy(alpha = 0.5f) else LuminaBorder
                    ),
                    RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (!isUser) {
                            Image(
                                painter = painterResource(id = R.drawable.lumina_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LUMINA 9D • ${message.model}",
                                color = LuminaCyan,
                                fontSize = 7.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = "YOU (OPERATIVE)",
                                color = LuminaPurple,
                                fontSize = 7.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (!isUser) {
                        IconButton(
                            onClick = onCopy,
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = LuminaTextSecondary,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.text,
                    color = LuminaTextPrimary,
                    fontSize = 9.sp,
                    lineHeight = 13.sp
                )
            }
        }
    }
}
