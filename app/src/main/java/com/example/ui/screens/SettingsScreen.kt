package com.example.ui.screens

import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.api.GeminiApiHelper
import com.example.data.model.UserAccount
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
fun SettingsScreen(
    userAccount: UserAccount,
    onBack: () -> Unit,
    onOpenStore: () -> Unit,
    onLogout: () -> Unit,
    onOpenWhatsApp: (Context, String) -> Unit,
    onShowFeedbackToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showFeedbackDialog by remember { mutableStateOf(false) }
    var feedbackText by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(5) }

    var apiKeyInput by remember { mutableStateOf(GeminiApiHelper.customApiKey ?: "") }
    var isHapticsEnabled by remember { mutableStateOf(true) }
    var isSfxEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuminaBackground)
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                    modifier = Modifier.testTag("back_button_settings")
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
                        text = "SETTINGS",
                        color = LuminaCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "App Feedback, API Keys & Support",
                        color = LuminaTextMuted,
                        fontSize = 8.sp
                    )
                }
            }
        }

        // Operative Profile Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(LuminaCardBg)
                .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.lumina_logo),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, LuminaCyan, CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userAccount.name,
                    color = LuminaTextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userAccount.email,
                    color = LuminaTextSecondary,
                    fontSize = 8.sp
                )
                Text(
                    text = "Operative ID: ${userAccount.id} • Balance: ${userAccount.coins.toInt()} Coins",
                    color = LuminaGold,
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuminaGold.copy(alpha = 0.2f))
                    .border(1.dp, LuminaGold, RoundedCornerShape(6.dp))
                    .clickable { onOpenStore() }
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, null, tint = LuminaGold, modifier = Modifier.size(10.dp))
                    Text("TOP UP", color = LuminaGold, fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Gemini API Configuration
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(LuminaCardBg)
                .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Key, null, tint = LuminaCyan, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "GEMINI API KEY CONFIGURATION",
                    color = LuminaCyan,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Enter a custom key or use the key injected from the AI Studio Secrets panel (.env / BuildConfig).",
                color = LuminaTextMuted,
                fontSize = 7.5.sp
            )

            OutlinedTextField(
                value = apiKeyInput,
                onValueChange = { apiKeyInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                placeholder = {
                    Text("AIzaSy... (leave blank for default auto-injection)", color = LuminaTextMuted, fontSize = 8.sp)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LuminaCyan,
                    unfocusedBorderColor = LuminaBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(6.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuminaCyan.copy(alpha = 0.2f))
                    .border(1.dp, LuminaCyan, RoundedCornerShape(6.dp))
                    .clickable {
                        GeminiApiHelper.customApiKey = apiKeyInput.trim()
                        onShowFeedbackToast("Gemini API Key configuration saved!")
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SAVE & TEST KEY",
                    color = LuminaCyan,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // System Toggles
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(LuminaCardBg)
                .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "AUDIO & HAPTIC FEEDBACK",
                color = LuminaTextSecondary,
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VolumeUp, null, tint = LuminaPurple, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cyber SFX Audio", color = Color.White, fontSize = 9.sp)
                }
                Switch(
                    checked = isSfxEnabled,
                    onCheckedChange = { isSfxEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = LuminaPurple, checkedTrackColor = LuminaPurple.copy(alpha = 0.4f))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Vibration, null, tint = LuminaCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Tactile Haptics", color = Color.White, fontSize = 9.sp)
                }
                Switch(
                    checked = isHapticsEnabled,
                    onCheckedChange = { isHapticsEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = LuminaCyan, checkedTrackColor = LuminaCyan.copy(alpha = 0.4f))
                )
            }
        }

        // Support & Feedback Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(LuminaCardBg)
                .border(1.dp, LuminaBorder, RoundedCornerShape(10.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "APP FEEDBACK & WHATSAPP SUPPORT",
                color = LuminaTextSecondary,
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold
            )

            // Feedback button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuminaPurple.copy(alpha = 0.15f))
                    .border(1.dp, LuminaPurple, RoundedCornerShape(6.dp))
                    .clickable { showFeedbackDialog = true }
                    .padding(10.dp)
                    .testTag("app_feedback_button"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Feedback, null, tint = LuminaPurple, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SUBMIT APP FEEDBACK",
                        color = LuminaPurple,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // WhatsApp Support button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuminaGreen.copy(alpha = 0.15f))
                    .border(1.dp, LuminaGreen, RoundedCornerShape(6.dp))
                    .clickable { onOpenWhatsApp(context, "+15559824110") }
                    .padding(10.dp)
                    .testTag("whatsapp_support_button"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.Chat, null, tint = LuminaGreen, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "CONTACT WHATSAPP SUPPORT",
                        color = LuminaGreen,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Account Switch / Logout
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(LuminaRed.copy(alpha = 0.15f))
                .border(1.dp, LuminaRed, RoundedCornerShape(8.dp))
                .clickable { onLogout() }
                .padding(10.dp)
                .testTag("logout_button"),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Logout, null, tint = LuminaRed, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SWITCH ACCOUNT / LOGOUT",
                    color = LuminaRed,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = "LUMINA 9D AI SUITE • VERSION 9.4.2 (RELEASE)",
            color = LuminaTextMuted,
            fontSize = 7.5.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    // Feedback Dialog
    if (showFeedbackDialog) {
        AlertDialog(
            onDismissRequest = { showFeedbackDialog = false },
            containerColor = LuminaSurface,
            title = {
                Text("App Feedback", color = LuminaCyan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("How would you rate your LUMINA 9D experience?", color = LuminaTextSecondary, fontSize = 9.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        (1..5).forEach { star ->
                            Icon(
                                imageVector = if (star <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "$star stars",
                                tint = LuminaGold,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { rating = star }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        placeholder = { Text("What features would you like to see in 9D Suite?", color = LuminaTextMuted, fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth().height(70.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LuminaCyan,
                            unfocusedBorderColor = LuminaBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showFeedbackDialog = false
                        onShowFeedbackToast("Feedback received! Thank you, Operative.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = LuminaCyan)
                ) {
                    Text("SUBMIT", color = Color.Black, fontSize = 8.5.sp, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showFeedbackDialog = false }) {
                    Text("CANCEL", color = LuminaTextMuted, fontSize = 8.5.sp)
                }
            }
        )
    }
}
