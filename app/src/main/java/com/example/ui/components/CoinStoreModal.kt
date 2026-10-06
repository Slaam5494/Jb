package com.example.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import com.example.data.model.CoinPackage
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
fun CoinStoreModal(
    currentBalance: Double,
    adsWatchedToday: Int,
    packages: List<CoinPackage>,
    onBuyPackage: (method: String, pkgId: String) -> Unit,
    onWatchRewardedAd: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPackageId by remember { mutableStateOf(packages.getOrNull(0)?.id ?: "pkg_1") }
    var selectedPaymentMethod by remember { mutableStateOf("EasyPaisa") }
    val paymentMethods = listOf("EasyPaisa", "JazzCash", "UPaisa/Load", "Debit Card")

    Dialog(onDismissRequest = onClose) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .border(BorderStroke(1.5.dp, LuminaGold), RoundedCornerShape(14.dp)),
            color = LuminaBackground
        ) {
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Modal Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = LuminaGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LUMINA COIN STORE",
                            color = LuminaGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    }

                    IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = LuminaRed, modifier = Modifier.size(16.dp))
                    }
                }

                Text(
                    text = "Select package & payment option to upgrade your AI generation capacity.",
                    color = LuminaTextMuted,
                    fontSize = 7.5.sp
                )

                // Balance Pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(LuminaSurface)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("ACTIVE BALANCE", color = LuminaTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${String.format("%.1f", currentBalance)} COINS",
                        color = LuminaGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Package List
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    packages.forEach { pkg ->
                        val isSelected = selectedPackageId == pkg.id
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) LuminaGold.copy(alpha = 0.15f)
                                    else LuminaCardBg
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) LuminaGold else LuminaBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedPackageId = pkg.id }
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = pkg.title,
                                        color = if (isSelected) LuminaGold else LuminaCyan,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (pkg.isPopular) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(LuminaPink)
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        ) {
                                            Text("HOT", color = Color.White, fontSize = 6.sp, fontWeight = FontWeight.Black)
                                        }
                                    }
                                }
                                Text(
                                    text = pkg.validityText,
                                    color = LuminaTextSecondary,
                                    fontSize = 7.5.sp
                                )
                            }

                            Text(
                                text = pkg.priceText,
                                color = LuminaGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                // Payment Methods
                Text(
                    text = "SELECT PAYMENT METHOD",
                    color = LuminaCyan,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    paymentMethods.forEach { method ->
                        val isSelected = selectedPaymentMethod == method
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) LuminaGreen.copy(alpha = 0.25f) else LuminaSurface)
                                .border(1.dp, if (isSelected) LuminaGreen else LuminaBorder, RoundedCornerShape(6.dp))
                                .clickable { selectedPaymentMethod = method }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = method,
                                color = if (isSelected) LuminaGreen else LuminaTextSecondary,
                                fontSize = 7.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Checkout Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.horizontalGradient(listOf(LuminaGreen, Color(0xFF128C7E)))
                        )
                        .clickable { onBuyPackage(selectedPaymentMethod, selectedPackageId) }
                        .padding(vertical = 10.dp)
                        .testTag("checkout_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, null, tint = Color.White, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "PROCEED TO PAY ($selectedPaymentMethod)",
                            color = Color.White,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Free Coins via Rewarded Ad (Daily 3 limit)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(LuminaCyan.copy(alpha = 0.1f))
                        .border(1.dp, LuminaCyan, RoundedCornerShape(8.dp))
                        .clickable {
                            onClose()
                            onWatchRewardedAd()
                        }
                        .padding(8.dp)
                        .testTag("watch_ad_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PlayCircle, null, tint = LuminaCyan, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (adsWatchedToday >= 3) "DAILY LIMIT REACHED (3/3 ADS WATCHED)"
                                   else "WATCH AD TO EARN CALL/VIDEO BALANCE ($adsWatchedToday/3)",
                            color = if (adsWatchedToday >= 3) LuminaTextMuted else LuminaCyan,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RewardedAdPlayerDialog(
    countdown: Int,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { /* Cannot dismiss until ad completes */ }) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .border(BorderStroke(2.dp, LuminaPink), RoundedCornerShape(14.dp)),
            color = Color(0xFF030712)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("SPONSOR TRANSMISSION", color = LuminaPink, fontSize = 9.sp, fontWeight = FontWeight.Black)
                    Text("REWARD IN ${countdown}s", color = LuminaCyan, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF1E1035), Color(0xFF0F2042))
                            )
                        )
                        .border(1.dp, LuminaPurple, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = LuminaCyan,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("LUMINA AI PARTNER SPONSOR", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("Transmitting holographic quantum neural ad...", color = LuminaTextMuted, fontSize = 7.5.sp)
                    }
                }

                Text(
                    text = "Keep transmission open to receive +1.0 Coin credit.",
                    color = LuminaTextSecondary,
                    fontSize = 7.5.sp
                )
            }
        }
    }
}
