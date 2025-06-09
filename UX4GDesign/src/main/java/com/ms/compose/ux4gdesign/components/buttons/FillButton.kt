package com.ms.compose.ux4gdesign.components.buttons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ms.compose.ux4gdesign.theme.UX4G_primary


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FillButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null, // Optional long-click
    startIcon: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
    iconSpacing: Dp = 5.dp,
    cornerRadius: Dp = 8.dp,
    backgroundColor: Color = UX4G_primary,
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = 28.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .combinedClickable( // Use combinedClickable for long press
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.White), // Use textColor for ripple
                enabled = enabled,
                onClick = onClick,
                onLongClick = onLongClick ?: {} // Handle nullable onLongClick
            )
            .padding(horizontal = 16.dp),
        color = Color.Transparent,
        contentColor = textColor
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (startIcon != null) {
                startIcon()
                Spacer(modifier = Modifier.width(iconSpacing))
            }

            Text(
                text = text,
                style = textStyle,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (endIcon != null) {
                Spacer(modifier = Modifier.width(iconSpacing))
                endIcon()
            }
        }
    }
}