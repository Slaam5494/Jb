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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.LuminaBackground
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.LuminaSurface
import com.example.ui.theme.LuminaTextMuted
import com.example.ui.theme.LuminaTextSecondary

@Composable
fun LoginScreen(
    onLoginWithGoogle: () -> Unit,
    onContinueAsGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0A1930),
                        LuminaBackground
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Glowing Logo Container
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, LuminaCyan), RoundedCornerShape(20.dp))
                    .background(LuminaCyan.copy(alpha = 0.1f))
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lumina_logo),
                    contentDescription = "Lumina 9D Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Title & Subtitle
            Text(
                text = "LUMINA AI",
                color = LuminaCyan,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "AI MOVIE MAKER & 9D SUITE",
                color = LuminaTextMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Box
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LuminaSurface.copy(alpha = 0.75f))
                    .border(BorderStroke(1.dp, LuminaCyan.copy(alpha = 0.4f)), RoundedCornerShape(12.dp))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Login With Google
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEA4335))
                        .clickable { onLoginWithGoogle() }
                        .padding(vertical = 12.dp)
                        .testTag("login_google_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Google",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "LOGIN WITH GMAIL",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }

                // Continue As Guest
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(LuminaCyan.copy(alpha = 0.15f))
                        .border(1.dp, LuminaCyan, RoundedCornerShape(8.dp))
                        .clickable { onContinueAsGuest() }
                        .padding(vertical = 12.dp)
                        .testTag("login_guest_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Guest",
                            tint = LuminaCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "CONTINUE AS GUEST",
                            color = LuminaCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }
        }
    }
}
