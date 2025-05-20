package com.ms.compose.ux4gdesign.components.switchButton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSwitchButton(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbContent: (@Composable () -> Unit)? = null,
    scale: Float = 1f,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        uncheckedThumbColor = Color.White,
        checkedTrackColor = Color(0xFF22C55E),
        uncheckedTrackColor = Color.Gray,
        disabledCheckedThumbColor = Color.LightGray,
        disabledUncheckedThumbColor = Color.LightGray,
        disabledCheckedTrackColor = Color(0xFFBDBDBD),
        disabledUncheckedTrackColor = Color(0xFFBDBDBD)
    )
) {
    Box(
        modifier = modifier
            .scale(scale)
            .alpha(if (enabled) 1f else 0.6f),
        contentAlignment = Alignment.Center
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            interactionSource = interactionSource,
            thumbContent = thumbContent,
            colors = colors
        )
    }
}
