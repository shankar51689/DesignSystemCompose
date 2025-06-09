package com.ms.compose.ux4gdesign.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    startIcon: (@Composable (() -> Unit))? = null,
    endIcon: (@Composable (() -> Unit))? = null,
    iconPadding: Dp = 4.dp,
    strokeWidth: Dp = 2.dp,
    cornerRadius: Dp = 8.dp,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    contentPadding: PaddingValues = PaddingValues(
        start = 10.dp,
        top = 8.dp,
        end = 10.dp,
        bottom = 8.dp
    )
) {
    val rippleColor = buttonColor.copy(alpha = 0.3f)

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .border(strokeWidth, buttonColor, RoundedCornerShape(cornerRadius)),
        color = Color.White,
        shape = RoundedCornerShape(cornerRadius),
        shadowElevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = rippleColor),
                    onClick = onClick
                )
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Start Icon
            if (startIcon != null) {
                startIcon()
                Spacer(modifier = Modifier.width(iconPadding))
            }

            Text(
                text = text,
                color = buttonColor,
                style = textStyle
            )

            // End Icon
            if (endIcon != null) {
                Spacer(modifier = Modifier.width(iconPadding))
                endIcon()
            }
        }
    }
}
