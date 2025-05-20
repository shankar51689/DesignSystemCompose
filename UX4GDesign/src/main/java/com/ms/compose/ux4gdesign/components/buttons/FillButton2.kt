package com.ms.compose.ux4gdesign.components.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FillButton2(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    cornerRadius: Dp = 8.dp,
    iconStart: ImageVector? = null,
    iconEnd: ImageVector? = null,
    iconSize: Dp = 14.dp,
    drawablePadding: Dp = 8.dp,
    textStyle: TextStyle = TextStyle(fontSize = 14.sp),
    paddingValues: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
) {
    // State to track press
    var pressed by remember { mutableStateOf(false) }

    // Animate scale based on pressed state
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scale"
    )

    // Trigger click effect when pressed becomes true
    LaunchedEffect(pressed) {
        if (pressed) {
            // Wait for press animation
            delay(100)
            // Release animation by resetting pressed
            pressed = false
            // Give time for release animation to play
            delay(100)
            // Invoke click callback
            onClick()
        }
    }

    Surface(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = contentColor.copy(alpha = 0.3f)),
                onClick = {
                    // Start press animation
                    pressed = true
                }
            ),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .padding(paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            iconStart?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize)
                )
                Spacer(modifier = Modifier.width(drawablePadding))
            }

            Text(
                text = text,
                style = textStyle,
                color = contentColor
            )

            iconEnd?.let {
                Spacer(modifier = Modifier.width(drawablePadding))
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}
