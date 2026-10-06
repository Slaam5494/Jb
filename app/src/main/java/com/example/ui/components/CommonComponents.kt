package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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
fun LuminaHeader(
    coinBalance: Double,
    onOpenStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(LuminaBackground.copy(alpha = 0.95f))
            .border(BorderStroke(1.dp, LuminaBorder), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.testTag("header_logo_group")
        ) {
            Image(
                painter = painterResource(id = R.drawable.lumina_logo),
                contentDescription = "Lumina Logo",
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, LuminaCyan, RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "LUMINA AI ",
                    color = LuminaCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MOVIE MAKER",
                    color = LuminaPink,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                )
            }
        }

        // Coins & Buy button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Coin Pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(LuminaGold.copy(alpha = 0.15f))
                    .border(1.dp, LuminaGold, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = "Coins",
                    tint = LuminaGold,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format("%.1f", coinBalance),
                    color = LuminaGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Buy Coins Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(LuminaPink, LuminaPurple)
                        )
                    )
                    .clickable { onOpenStore() }
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .testTag("buy_coin_button"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Coins",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "BUY COINS",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AdBannerSlot(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(LuminaCyan.copy(alpha = 0.04f))
            .border(BorderStroke(1.dp, LuminaBorder), RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "--- ADVERTISEMENT SLOT (TAP TO EARN COINS) ---",
            color = LuminaTextMuted,
            fontSize = 7.5.sp,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(LuminaSurface.copy(alpha = 0.85f))
                .border(1.dp, LuminaPurple.copy(alpha = 0.4f), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = LuminaPurple,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    color = LuminaPurple,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun TickerBar(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(LuminaCyan.copy(alpha = 0.06f))
            .border(1.dp, LuminaBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Campaign,
            contentDescription = "Announcement",
            tint = LuminaCyan,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = message,
            color = LuminaTextSecondary,
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
