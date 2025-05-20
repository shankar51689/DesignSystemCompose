package com.ms.compose.designsystemcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ms.compose.designsystemcompose.ui.theme.UX4GDesignSystemTheme
import com.ms.compose.ux4gdesign.components.buttons.FillButton
import com.ms.compose.ux4gdesign.components.buttons.FillButton2
import com.ms.compose.ux4gdesign.components.editTextView.CustomEditText
import com.ms.compose.ux4gdesign.components.editTextView.CustomMultiLineEditText
import com.ms.compose.ux4gdesign.components.editTextView.EditTextState
import com.ms.compose.ux4gdesign.components.editTextView.TextFieldState
import com.ms.compose.ux4gdesign.components.imageViewComponent.ImageWithDynamicGreenDot
import com.ms.compose.ux4gdesign.components.listGroup.CustomListCard1Compose
import com.ms.compose.ux4gdesign.components.listGroup.CustomListCard2Compose
import com.ms.compose.ux4gdesign.components.listGroup.CustomListCard3
import com.ms.compose.ux4gdesign.components.loaderView.CircularLoader
import com.ms.compose.ux4gdesign.components.progressbarComponent.CircularProgressBar
import com.ms.compose.ux4gdesign.components.progressbarComponent.HalfCircularProgressBar
import com.ms.compose.ux4gdesign.components.progressbarComponent.LinearProgressBar2Compose
import com.ms.compose.ux4gdesign.components.rangeBar.CustomRangeSlider
import com.ms.compose.ux4gdesign.components.switchButton.CustomSwitchButton
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {

    val imageUrl = "https://images.pexels.com/photos/1759531/pexels-photo-1759531.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
    var fieldState by remember { mutableStateOf(TextFieldState.DEFAULT) }
    var message by remember { mutableStateOf("") }

    UX4GDesignSystemTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState()),
            ) {
                FillButton(
                    text = "Button",
                    onClick = {

                    }
                )

                FillButton2(
                    text = "Button",
                    onClick = {

                    }
                )

                GreetingPreview(imageUrl)

                var name by remember { mutableStateOf("") }
                var state by remember { mutableStateOf(TextFieldState.DEFAULT) }
                var state2 by remember { mutableStateOf(EditTextState.DEFAULT) }
                var message by remember { mutableStateOf("") }

                CustomEditText(
                    value = name,
                    onValueChange = {
                        name = it
                        if (it.isBlank()) {
                            state = TextFieldState.ERROR
                            message = "Name cannot be empty"
                        } else {
                            state = TextFieldState.SUCCESS
                            message = "Looks good!"
                        }
                    },
                    labelText = "Full Name",
                    hintText = "Enter your name",
                    state = state,
                    messageText = message,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                CustomMultiLineEditText(
                    labelText = "Description",
                    hintText = "Enter your text...",
                    text = name,
                    textSize = 25.sp,
                    onTextChange = {
                        name = it
                        state2 = when {
                            it.length > 150 -> EditTextState.WARNING
                            it.length > 180 -> EditTextState.ERROR
                            it.isNotEmpty() -> EditTextState.SUCCESS
                            else -> EditTextState.DEFAULT
                        }
                    },
                    state = state2
                )

                CircularLoader(
                    modifier = Modifier.size(60.dp),
                    loaderColor = Color.Red,
                    strokeWidth = 6.dp
                )
                loaderScreen()
                RangeSilder()
                ProgressBarDemoScreen()
                SwitchDemo()
                ListGroupViews()
            }
        }
    }
}


@Composable
fun ListGroupViews() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CustomListCard1Compose(
            titleText = "Wi-Fi",
            supportingText = "Connected to Home Network",
            showSwitch = true
        )

        CustomListCard1Compose(
            titleText = "Location",
            supportingText = "Access your location",
            showCheckBox = true
        )

        CustomListCard1Compose(
            titleText = "Account",
            supportingText = "Manage your account settings",
            showArrow = true,
            onArrowClick = {
                // Perform your navigation or action here
                println("Arrow clicked!")
            }
        )

        CustomListCard1Compose(
            titleText = "Theme",
            supportingText = "Select theme preference",
            showRadioBtn = true,
            setComponentsOnLeft = true,
            cardBackgroundColor = Color(0xFFECEFF1)
        )

        Column() {
            CustomListCard2Compose(
                titleText = "Settings",
                supportingText = "Manage notification preferences",
                showCheckBox = true,
                showSwitch = false,
                showArrow = false,
                showRadioBtn = false,
                onCheckBoxClick = { it ->
                    // Handle arrow click
                    println("Arrow clicked!1")
                }
            )
            CustomListCard2Compose(
                titleText = "Settings",
                supportingText = "Manage notification preferences",
                showCheckBox = false,
                showSwitch = true,
                showArrow = false,
                showRadioBtn = false,
                onSwitchClick = { it ->
                    // Handle arrow click
                    println("Arrow clicked!2")
                }
            )
            CustomListCard2Compose(
                titleText = "Settings",
                supportingText = "Manage notification preferences",
                showCheckBox = false,
                showSwitch = false,
                showArrow = true,
                showRadioBtn = false,
                onArrowClick = {
                    // Handle arrow click
                    println("Arrow clicked!3")
                }
            )
            CustomListCard2Compose(
                titleText = "Settings",
                supportingText = "Manage notification preferences",
                showCheckBox = false,
                showSwitch = false,
                showArrow = false,
                showRadioBtn = true,
                onRadioButtonClick = { it ->
                    // Handle arrow click
                    println("Arrow clicked!4")
                }
            )

            CustomListCard3(
                titleText = "Wi-Fi",
                supportingText = "Tap to configure",
                iconPainter = painterResource(com.ms.compose.ux4gdesign.R.drawable.ic_wifi),
                imagePainter = painterResource(com.ms.compose.ux4gdesign.R.drawable.ic_setting),
                showCheckBox = false,
                showArrow = true,
                showSwitch = false,
                showRadioBtn = false,
                onCheckBoxClick = { println("Checkbox clicked: $it") },
                onRadioButtonClick = { println("Radio selected: $it") },
                onSwitchClick = { println("Switch isOn: $it") },
                onArrowClick = { println("Arrow icon clicked") }
            )
        }
    }
}

@Composable
fun loaderScreen() {
    // Example progress value (simulate progress)
    var progress by remember { mutableStateOf(0f) }

    // Simulate progress update (for demo/testing)
    LaunchedEffect(Unit) {
        while (progress < 100f) {
            delay(100) // delay 100ms
            progress += 1f
        }
    }

    // UI Layout
    Box(
        modifier = Modifier
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Row() {
            CircularProgressBar(
                progress = progress,
                progressColor = Color(0xFF6A40FF),
                trackColor = Color(0xFFE0E0E0),
                modifier = Modifier.size(80.dp)
            )
            CircularProgressBar(
                progress = progress,
                progressColor = Color(0xFF6A40FF),
                trackColor = Color(0xFFE0E0E0),
                modifier = Modifier.size(130.dp)
            )
        }

    }
}


@Composable
fun RangeSilder() {
    var leftValue by remember { mutableFloatStateOf(20f) }
    var rightValue by remember { mutableFloatStateOf(80f) }
    CustomRangeSlider(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),  // enough height for thumbs + labels
        minValue = 0f,
        maxValue = 100f,
        initialLeftValue = leftValue,
        initialRightValue = rightValue,
        trackColor = Color(0xFF3DDC84),            // Android green
        inactiveTrackColor = Color.LightGray,
        thumbBorderColor = Color.DarkGray,
        thumbFillColor = Color.White,
        labelColor = Color.Black,
        labelTextSize = 14.sp,
        labelSuffix = "%",
        onRangeChanged = { newLeft, newRight ->
            leftValue = newLeft
            rightValue = newRight
        }
    )
}

@Composable
fun ProgressBarDemoScreen() {
    // simulate dynamic progress
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(100L)
            progress = (progress + 1f).coerceAtMost(100f)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        // Small version: size <= smallThreshold → label outside
        HalfCircularProgressBar(
            progress        = progress,
            size            = 80.dp,
            smallThreshold  = 95.dp,
            labelText       = "Uploading",
        )

        // Large version: size > smallThreshold → label inside
        HalfCircularProgressBar(
            progress        = progress,
            size            = 150.dp,
            smallThreshold  = 95.dp,
            labelText       = "Loading",
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Side Label Mode
        LinearProgressBar2Compose(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            showPercentage = false,
            showDownLabel = false
        )
        LinearProgressBar2Compose(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            showPercentage = true,
            showDownLabel = false
        )

        Spacer(modifier = Modifier.height(0.dp))

        // Down Label Mode
        LinearProgressBar2Compose(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            showPercentage = true,
            showDownLabel = true
        )
    }
}

@Composable
fun SwitchDemo() {
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        CustomSwitchButton(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            scale = 1.3f
        )

        Text(
            text = if (isChecked) "Switch is ON" else "Switch is OFF",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview(imageUrl : String = "") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ImageWithDynamicGreenDot(
            imageResId = R.drawable.test,
            imageSize = 100.dp,
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(24.dp))

        // For square image
        ImageWithDynamicGreenDot(
            imageResId = R.drawable.test,
            imageSize = 160.dp,
            shape = RoundedCornerShape(10.dp)
        )
    }
}


