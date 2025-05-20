package com.ms.compose.ux4gdesign.components.progressbarComponent

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier.size(150.dp),
    progress: Float,
    trackColor: Color = Color(0xFFE0E0E0),
    progressColor: Color = Color(0xFF6A40FF),
    strokeFraction: Float = 0.1f,
    showLabel: Boolean = true,
    labelText: String = "Uploading",
    thresholdDp: Dp = 100.dp,
    labelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
    textStyle: TextStyle = TextStyle(fontSize = 14.sp, color = Color.Black)
) {
    // Animate progress change
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 100f),
        animationSpec = tween(durationMillis = 500)
    )

    BoxWithConstraints(
        modifier = modifier
            .background(color = Color.White)
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        val canvasSize = min(maxWidth, maxHeight)
        val strokeWidth = canvasSize * strokeFraction

        Canvas(modifier = Modifier.fillMaxSize()) {
            // this.size is the DrawScope.size (an androidx.compose.ui.geometry.Size)
            val fullSize = kotlin.math.min(this.size.width, this.size.height)
            val strokePx  = strokeWidth.toPx()
            val inset     = strokePx / 2f
            val diameter  = fullSize - strokePx

            val topLeft = Offset(inset, inset)
            val arcSize = androidx.compose.ui.geometry.Size(diameter, diameter)

            // track
            drawArc(
                color     = trackColor,
                startAngle= 0f,
                sweepAngle= 360f,
                useCenter = false,
                topLeft   = topLeft,
                size      = arcSize,
                style     = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
            // progress
            drawArc(
                color     = progressColor,
                startAngle= -90f,
                sweepAngle= 360f * (animatedProgress / 100f),
                useCenter = false,
                topLeft   = topLeft,
                size      = arcSize,
                style     = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }



        if(showLabel) {
            // 3) Label inside vs outside
            if (canvasSize < thresholdDp) {
                // 2) Percentage always centered
                Text(
                    text    = "${animatedProgress.toInt()}%",
                    style   = textStyle.copy(fontSize = (canvasSize.value * 0.2).sp, fontWeight = FontWeight.Bold),
                    modifier= Modifier.align(Alignment.Center)
                )

                Text(
                    text    = labelText,
                    style   = textStyle.copy(fontSize = (canvasSize.value * 0.17).sp, color = labelColor),
                    modifier= Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = (canvasSize.value * 0.26).dp)
                )

            } else {
                // Progress percentage text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val percentText = "${animatedProgress.toInt()}%"
                    Text(
                        text = percentText,
                        style = textStyle.copy(fontSize = (canvasSize.value * 0.2).sp, fontWeight = FontWeight.Bold)
                    )

                    if (showLabel) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = labelText,
                            style = textStyle.copy(
                                fontSize = (canvasSize.value * 0.1).sp,
                                color = labelColor
                            )
                        )
                    }
                }
            }
        }

    }
}
