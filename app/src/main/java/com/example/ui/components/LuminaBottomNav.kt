package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ModuleType
import com.example.ui.theme.LuminaBorder
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.LuminaGreen
import com.example.ui.theme.LuminaPink
import com.example.ui.theme.LuminaSurface
import com.example.ui.theme.LuminaTextMuted

data class NavItemData(
    val module: ModuleType,
    val label: String,
    val icon: ImageVector,
    val activeColor: Color = LuminaCyan
)

@Composable
fun LuminaBottomNav(
    currentModule: ModuleType,
    onSelectModule: (ModuleType) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItemData(ModuleType.MOVIE_MAKER, "MOVIE", Icons.Default.Movie, LuminaPink),
        NavItemData(ModuleType.MEDIA_GEN, "MEDIA", Icons.Default.PermMedia, LuminaCyan),
        NavItemData(ModuleType.AI_CHAT, "AI CHAT", Icons.Default.Chat, LuminaCyan),
        NavItemData(ModuleType.PROMPT_STUDIO, "PROMPT", Icons.Default.AutoAwesome, LuminaCyan),
        NavItemData(ModuleType.FRIENDS_HUB, "FRIENDS", Icons.Default.Group, LuminaGreen),
        NavItemData(ModuleType.SETTINGS, "SETTING", Icons.Default.Settings, LuminaCyan)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LuminaSurface.copy(alpha = 0.95f))
            .border(1.dp, LuminaBorder, RoundedCornerShape(8.dp))
            .padding(vertical = 6.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentModule == item.module
            val color = if (isSelected) item.activeColor else LuminaTextMuted

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelectModule(item.module) }
                    .testTag("nav_item_${item.label.lowercase()}")
                    .padding(vertical = 2.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.label,
                    color = if (isSelected) Color.White else LuminaTextMuted,
                    fontSize = 7.sp,
                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                    letterSpacing = 0.3.sp
                )
            }
        }
    }
}
