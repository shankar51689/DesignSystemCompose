package com.ms.compose.ux4gdesign.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ms.compose.ux4gdesign.theme.UX4G_primary
import kotlinx.coroutines.delay

@Composable
fun FillButton2(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = UX4G_primary,
    contentColor: Color = Color.White,
    cornerRadius: Dp = 8.dp,
    iconStart: (@Composable () -> Unit)? = null,
    iconEnd: (@Composable () -> Unit)? = null,
    drawablePadding: Dp = 8.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp) // Changed
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scale"
    )

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
            delay(100)
            onClick()
        }
    }

    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = 28.dp) // Added min size
            .scale(scale)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = contentColor.copy(alpha = 0.3f)),
                onClick = { pressed = true }
            ),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.padding(paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            iconStart?.invoke()
            if (iconStart != null) {
                Spacer(modifier = Modifier.width(drawablePadding))
            }

            Text(
                text = text,
                style = textStyle,
                color = contentColor
            )

            if (iconEnd != null) {
                Spacer(modifier = Modifier.width(drawablePadding))
            }
            iconEnd?.invoke()
        }
    }
}