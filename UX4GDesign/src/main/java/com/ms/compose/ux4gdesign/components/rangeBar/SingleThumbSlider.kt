import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SingleThumbSlider(
    modifier: Modifier = Modifier,
    minValue: Float = 0f,
    maxValue: Float = 100f,
    initialValue: Float = 50f,
    trackHeight: Dp = 5.dp,
    thumbSize: Dp = 14.dp,
    trackColor: Color = Color.Red,
    inactiveTrackColor: Color = Color.Gray,
    thumbBorderColor: Color = Color.Blue,
    thumbFillColor: Color = Color.White,
    labelColor: Color = Color.Black,
    labelTextSize: TextUnit = 14.sp,
    showLabel: Boolean = true,
    labelSuffix: String = "%",
    onValueChanged: (Float) -> Unit
) {
    val density = LocalDensity.current
    val thumbRadiusPx = with(density) { thumbSize.toPx() }
    val trackHeightPx = with(density) { trackHeight.toPx() }
    val textSizePx = with(density) { labelTextSize.toPx() }

    var thumbValue by remember { mutableStateOf(initialValue) }
    var canvasWidth by remember { mutableStateOf(0f) }

    val padding = thumbRadiusPx

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x
                    val proportion = ((x - padding) / (canvasWidth - 2 * padding)).coerceIn(0f, 1f)
                    val value = minValue + proportion * (maxValue - minValue)

                    thumbValue = value.coerceIn(minValue, maxValue)
                    onValueChanged(thumbValue)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            canvasWidth = size.width
            val centerY = size.height / 2
            val trackStart = padding
            val trackEnd = size.width - padding

            val thumbX = trackStart + ((thumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)

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
                topLeft = Offset(trackStart, centerY - trackHeightPx / 2),
                size = Size(thumbX - trackStart, trackHeightPx),
                cornerRadius = CornerRadius(10f, 10f)
            )

            // Thumb
            drawCircle(
                color = thumbFillColor,
                radius = thumbRadiusPx,
                center = Offset(thumbX, centerY)
            )
            drawCircle(
                color = thumbBorderColor,
                radius = thumbRadiusPx,
                center = Offset(thumbX, centerY),
                style = Stroke(width = 6f)
            )

            // Label
            if (showLabel) {
                drawIntoCanvas { canvas ->
                    val label = "${thumbValue.toInt()}$labelSuffix"
                    val paint = android.graphics.Paint().apply {
                        color = labelColor.toArgb()
                        textSize = textSizePx
                        isAntiAlias = true
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    val yOffset = centerY + thumbRadiusPx + textSizePx
                    canvas.nativeCanvas.drawText(label, thumbX, yOffset, paint)
                }
            }
        }
    }
}
