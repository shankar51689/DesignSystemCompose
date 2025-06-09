import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoImageSlider2(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    autoScrollInterval: Long = 3000L,
    dotActiveColor: Color = Color.Black,
    dotInactiveColor: Color = Color.LightGray
) {
    if (imageUrls.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { imageUrls.size }
    )
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll logic
    LaunchedEffect(pagerState.currentPage) {
        delay(autoScrollInterval)
        val nextPage = (pagerState.currentPage + 1) % imageUrls.size
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            AsyncImage(
                model = imageUrls[page],
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            repeat(imageUrls.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(width = 24.dp, height = 8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) dotActiveColor else dotInactiveColor)
                )
            }
        }
    }
}
