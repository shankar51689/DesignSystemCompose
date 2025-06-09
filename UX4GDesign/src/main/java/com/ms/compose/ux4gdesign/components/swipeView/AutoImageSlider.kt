package com.ms.compose.ux4gdesign.components.swipeView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoImageSlider(
    imageUrls: List<String>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(200.dp), // Default size
    autoScrollInterval: Long = 3000L,
    activeColor: Color = Color.Black,
    inactiveColor: Color = Color.Gray
) {
    val pageCount = imageUrls.size
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll logic
    LaunchedEffect(pagerState.currentPage) {
        if (pageCount > 1) {
            delay(autoScrollInterval)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = modifier, // This defines the full size externally
        contentAlignment = Alignment.BottomCenter
    ) {
        // Wrap the pager inside a fixed-size Box to force size constraints
        HorizontalPager(
            state = pagerState,
            modifier = modifier // Force size of each page
        ) { page ->
            AsyncImage(
                model = imageUrls[page],
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = modifier.height(250.dp) // Force image to exactly fill the pager size
            )
        }

        // Dots indicator
        Row(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(8.dp)
                        .width(if (isSelected) 24.dp else 8.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(if (isSelected) activeColor else inactiveColor)
                )
            }
        }
    }
}