package com.ms.compose.ux4gdesign.components.listGroup

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    expanded: Boolean = false,
    cornerRadius: Dp = 8.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    strokeColor: Color = MaterialTheme.colorScheme.outline,
    strokeWidth: Dp = 1.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    contentTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onExpandChanged: (Boolean) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(expanded) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "arrowRotation"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
                onExpandChanged(isExpanded)
            },
        shape = RoundedCornerShape(cornerRadius),
        color = backgroundColor,
        border = BorderStroke(strokeWidth, strokeColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = titleTextStyle,
                    color = titleColor,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.rotate(rotationAngle),
                    tint = titleColor
                )
            }

            // Content
            if (isExpanded) {
                Text(
                    text = content,
                    style = contentTextStyle,
                    color = contentColor,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}