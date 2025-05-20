package com.ms.compose.ux4gdesign.components.loaderView

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularLoader(
    modifier: Modifier = Modifier,
    loaderColor: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp,
    animationDuration: Int = 1200
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotationTransition")

    val angle = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing)
        ),
        label = "angleAnimation"
    )

    Canvas(
        modifier = modifier
            .size(48.dp)
            .rotate(angle.value)
    ) {
        val stroke = strokeWidth.toPx()
        val arcRadius = size.minDimension / 2 - stroke / 2
        val center = Offset(size.width / 2, size.height / 2)
        drawArc(
            color = loaderColor,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round),
            topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
            size = Size(arcRadius * 2, arcRadius * 2)
        )
    }
}
