package com.ms.compose.ux4gdesign.components.progressbarComponent

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HalfCircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    size: Dp = 100.dp,
    smallThreshold: Dp = 95.dp,
    trackColor: Color = Color(0xFFE0E0E0),
    progressColor: Color = Color(0xFF6A40FF),
    showLabel: Boolean = true,
    labelText: String = "Uploading",
    labelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
) {
    // animate progress smoothly
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 100f),
        animationSpec = tween(durationMillis = 500)
    )

    // Compute height exactly like your View.onMeasure:
    val heightDp = remember(size) {
        if (size < 45.dp) size * 0.85f else size * 0.8f
    }

    BoxWithConstraints(
        modifier = modifier
            .width(size)
            .height(heightDp),
        contentAlignment = Alignment.TopCenter
    ) {
        val density = LocalDensity.current
        val widthPx = with(density) { size.toPx() }
        val smallPx = with(density) { smallThreshold.toPx() }
        val isSmall = widthPx < smallPx

        // stroke width and circle radius
        val strokePx = if (isSmall) widthPx * 0.14f else widthPx * 0.12f
        val radiusPx = (widthPx - strokePx) / 2f

        // Build the bounding rect for our half-circle
        val left   = strokePx / 2f
        val top    = strokePx / 2f
        val right  = widthPx - strokePx / 2f
        val bottom = widthPx  // we only draw half circle
        val arcSize = Size(right - left, bottom - top)

        // Draw the two arcs
        Canvas(modifier = Modifier.fillMaxSize()) {
            // track arc (180°)
            drawArc(
                color      = trackColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter  = false,
                topLeft    = Offset(left, top),
                size       = arcSize,
                style      = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
            // progress arc
            drawArc(
                color      = progressColor,
                startAngle = 180f,
                sweepAngle = 180f * (animated / 100f),
                useCenter  = false,
                topLeft    = Offset(left, top),
                size       = arcSize,
                style      = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        // Overlay percentage text
        val percentFontSize = with(density) { (widthPx * if (isSmall) 0.18f else 0.16f).toDp() }
        // vertical position: mimic your centerY - offset logic
        /*val percentOffsetY = if (isSmall) {
            // centerY = heightPx*0.6 → in dp:
            with(density) { (heightDp.toPx() * 0.2f - strokePx/2).toDp() }
            - percentFontSize / 2
        } else {
            // centerY = full height
            heightDp - radiusPx.toDp() * 1.1f - percentFontSize / 2
        }
*/
        // Calculate center of arc area
        val centerArcY = heightDp / 2

        // Adjust offset to truly center the text vertically
        val percentOffsetY = centerArcY - percentFontSize / 2

        if (showLabel) {
            if (isSmall) {
                // Small: keep label below arc
                /*Text(
                    text = labelText,
                    fontSize = 12.sp,
                    color = labelColor,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = 4.dp)
                )*/
            } else {
                // Large: place label above the percentage text
                val labelFont = (percentFontSize * 0.6f)

                Text(
                    text = labelText,
                    fontSize = labelFont.value.sp,
                    color = labelColor,
                    modifier = Modifier
                        .offset(y = percentOffsetY - labelFont - 4.dp)
                )
            }
        }


        Text(
            text = "${animated.toInt()}%",
            fontSize = percentFontSize.value.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(y = percentOffsetY + (if(!isSmall) 3.dp else 0.dp))
        )

        // Overlay label
        if (showLabel) {
            if (isSmall) {
                // outside below arc
                Text(
                    text = labelText,
                    fontSize = 12.sp,
                    color = labelColor,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = 4.dp)
                )
            } else {
                // inside, just above the arc
                val labelFont = (percentFontSize * 0.6f)

                /*Text(
                    text = labelText,
                    fontSize = labelFont.value.sp,
                    color = labelColor,
                    modifier = Modifier
                        .offset(y = percentOffsetY + percentFontSize + 4.dp)
                )*/
            }
        }
    }
}

@Composable
private fun Float.toDp(): Dp = Dp(this / LocalDensity.current.density)
