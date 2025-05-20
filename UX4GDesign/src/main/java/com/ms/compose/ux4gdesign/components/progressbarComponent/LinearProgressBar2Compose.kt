package com.ms.compose.ux4gdesign.components.progressbarComponent

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LinearProgressBar2Compose(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundBarColor: Color = Color(0xFFDEE3FF),
    progressBarColor: Color = Color(0xFF3F51B5),
    textColor: Color = Color.DarkGray,
    progressTextSize: TextUnit = 14.sp,
    showPercentage: Boolean = true,
    showDownLabel: Boolean = false,
) {
    BoxWithConstraints(modifier = modifier) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()

        val trackHeight = if (showDownLabel) heightPx / 5 else heightPx / 4
        val thumbRadius = if (showDownLabel) trackHeight / 1.8f else heightPx / 8f

        Canvas(modifier = Modifier.fillMaxSize()) {
            val progressRatio = progress.coerceIn(0f, 100f) / 100f
            val progressText = "${progress.toInt()}%"

            val paint = android.graphics.Paint().apply {
                color = textColor.toArgb()
                textSize = progressTextSize.toPx()
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.LEFT
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }

            val textWidth = paint.measureText(progressText)
            val textPadding = 10f
            val maxProgressWidth = if (showPercentage && !showDownLabel) widthPx - textWidth - textPadding else widthPx
            val progressWidth = maxProgressWidth * progressRatio

            val trackTop = if (showDownLabel) trackHeight / 2 else heightPx / 2 - trackHeight / 2
            val trackBottom = trackTop + trackHeight

            val backgroundRect = Rect(0f, trackTop, maxProgressWidth, trackBottom)
            val progressRect = Rect(0f, trackTop, progressWidth, trackBottom)

            // Background bar
            drawRoundRect(
                color = backgroundBarColor,
                topLeft = Offset(backgroundRect.left, backgroundRect.top),
                size = backgroundRect.size,
                cornerRadius = CornerRadius(trackHeight / 2, trackHeight / 2)
            )

            // Progress bar
            drawRoundRect(
                color = progressBarColor,
                topLeft = Offset(progressRect.left, progressRect.top),
                size = progressRect.size,
                cornerRadius = CornerRadius(trackHeight / 2, trackHeight / 2)
            )

            // Thumb
            drawCircle(
                color = progressBarColor,
                radius = thumbRadius,
                center = Offset(progressWidth, (trackTop + trackBottom) / 2)
            )

            // Percentage Text
            if (showPercentage) {
                drawIntoCanvas { canvas ->
                    val textX = if (showDownLabel) {
                        widthPx - textWidth - textPadding // Bottom-right
                    } else {
                        maxProgressWidth + textPadding   // Right of the bar
                    }

                    val textY = if (showDownLabel) {
                        trackBottom + progressTextSize.toPx() + 8f // Below the bar
                    } else {
                        heightPx / 2 + progressTextSize.toPx() / 3
                    }

                    canvas.nativeCanvas.drawText(progressText, textX, textY, paint)
                }
            }
        }
    }
} // END
