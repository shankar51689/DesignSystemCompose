package com.ms.compose.ux4gdesign.components.rangeBar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Composable
fun CustomRangeSlider(
    modifier: Modifier = Modifier,
    minValue: Float = 0f,
    maxValue: Float = 100f,
    initialLeftValue: Float = 0f,
    initialRightValue: Float = 100f,
    trackHeight: Dp = 5.dp,
    thumbSize: Dp = 14.dp,
    trackColor: Color = Color.Red,
    inactiveTrackColor: Color = Color.Gray,
    thumbBorderColor: Color = Color.Blue,
    thumbFillColor: Color = Color.White,
    labelColor: Color = Color.Black,
    labelTextSize: TextUnit = 14.sp,
    showLabels: Boolean = true,
    labelSuffix: String = "%",
    onRangeChanged: (Float, Float) -> Unit
) {
    val density = LocalDensity.current
    val thumbRadiusPx = with(density) { thumbSize.toPx() }
    val trackHeightPx = with(density) { trackHeight.toPx() }
    val textSizePx = with(density) { labelTextSize.toPx() }

    var leftThumbValue by remember { mutableStateOf(initialLeftValue) }
    var rightThumbValue by remember { mutableStateOf(initialRightValue) }
    var canvasWidth by remember { mutableStateOf(0f) }

    val padding = thumbRadiusPx

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x
                    val proportion = ((x - padding) / (canvasWidth - 2 * padding)).coerceIn(0f, 1f)
                    val value = minValue + proportion * (maxValue - minValue)

                    val isLeft = abs(value - leftThumbValue) < abs(value - rightThumbValue)
                    if (isLeft) {
                        leftThumbValue = (value.coerceIn(minValue, rightThumbValue - 1f))
                    } else {
                        rightThumbValue = (value.coerceIn(leftThumbValue + 1f, maxValue))
                    }
                    onRangeChanged(leftThumbValue, rightThumbValue)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            canvasWidth = size.width
            val centerY = size.height / 2
            val trackStart = padding
            val trackEnd = size.width - padding

            val leftX = trackStart + ((leftThumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)
            val rightX = trackStart + ((rightThumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)

            // Inactive track
            drawRoundRect(
                color = inactiveTrackColor,
                topLeft = Offset(trackStart, centerY - trackHeightPx / 2),
                size = Size(trackEnd - trackStart, trackHeightPx),
                cornerRadius = CornerRadius(10f, 10f)
            )

            // Active track
            drawRoundRect(
                color = trackColor,
                topLeft = Offset(leftX, centerY - trackHeightPx / 2),
                size = Size(rightX - leftX, trackHeightPx),
                cornerRadius = CornerRadius(10f, 10f)
            )

            // Thumbs
            drawCircle(
                color = thumbFillColor,
                radius = thumbRadiusPx,
                center = Offset(leftX, centerY)
            )
            drawCircle(
                color = thumbBorderColor,
                radius = thumbRadiusPx,
                center = Offset(leftX, centerY),
                style = Stroke(width = 6f)
            )
            drawCircle(
                color = thumbFillColor,
                radius = thumbRadiusPx,
                center = Offset(rightX, centerY)
            )
            drawCircle(
                color = thumbBorderColor,
                radius = thumbRadiusPx,
                center = Offset(rightX, centerY),
                style = Stroke(width = 6f)
            )

            // Labels
            if (showLabels) {
                drawIntoCanvas { canvas ->
                    val leftText = "${leftThumbValue.toInt()}$labelSuffix"
                    val rightText = "${rightThumbValue.toInt()}$labelSuffix"
                    val paint = android.graphics.Paint().apply {
                        color = labelColor.toArgb()
                        textSize = textSizePx
                        isAntiAlias = true
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    val yOffset = centerY + thumbRadiusPx + textSizePx
                    canvas.nativeCanvas.drawText(leftText, leftX, yOffset, paint)
                    canvas.nativeCanvas.drawText(rightText, rightX, yOffset, paint)
                }
            }
        }
    }
}
