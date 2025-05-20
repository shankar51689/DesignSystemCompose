package com.ms.compose.ux4gdesign.components.imageViewComponent

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ImageWithDynamicGreenDot(
    imageResId: Int,
    imageSize: Dp = 200.dp,
    shape: Shape = RoundedCornerShape(0.dp),
    dotSize: Dp = (imageSize / 9),
) {

    BoxWithConstraints(
        modifier = Modifier.size(imageSize)
    ) {
        val radius = maxWidth / 2
        val diagonalOffset = if (shape == CircleShape) {
            // Calculate inward offset from bottom-end corner along circle curve
            val angle45Offset = (radius.value * sin(Math.toRadians(55.0))).toFloat().dp
            radius - angle45Offset
        } else {
            val cornerPx = with(LocalDensity.current) {
                (shape as RoundedCornerShape).bottomEnd.toPx(
                    Size(maxWidth.toPx(), maxHeight.toPx()),
                    LocalDensity.current
                )
            }
            val cornerDp = with(LocalDensity.current) { cornerPx.toDp() }
            // inset along the 45° line of that quarter-circle
            cornerDp * (0.75f - (sin(Math.toRadians(95.0)).toFloat()))
        }

        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -diagonalOffset, y = -diagonalOffset)
                .size(dotSize)
                .background(Color.Green, shape = CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
    }

}
