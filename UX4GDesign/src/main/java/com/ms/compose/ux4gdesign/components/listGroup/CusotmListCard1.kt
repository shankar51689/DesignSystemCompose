package com.ms.compose.ux4gdesign.components.listGroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ms.compose.ux4gdesign.components.switchButton.CustomSwitchButton

@Composable
fun CustomListCard1Compose(
    modifier: Modifier = Modifier,
    setComponentsOnLeft: Boolean = false,
    titleText: String = "List Item",
    supportingText: String = "",
    titleTextFontSize: Dp = 14.sp.toDp(),
    supportingTextFontSize: Dp = 14.sp.toDp(),
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    supportingTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    showCheckBox: Boolean = false,
    showArrow: Boolean = false,
    showSwitch: Boolean = false,
    showRadioBtn: Boolean = false,
    onArrowClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        // The container that switches between two sets of title/supportingText
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (setComponentsOnLeft) {
                // Container 2 UI (like titleTextView2, supportingTextView2)
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = titleText,
                        fontSize = titleTextFontSize.toSp(),
                        color = titleColor,
                        fontWeight = FontWeight.Medium,
                    )
                    if (supportingText.isNotEmpty()) {
                        Text(
                            text = supportingText,
                            fontSize = supportingTextFontSize.toSp(),
                            color = supportingTextColor,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (showCheckBox) {
                        var checked by remember { mutableStateOf(false) }
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it }
                        )
                    }
                    if (showRadioBtn) {
                        var selected by remember { mutableStateOf(false) }
                        RadioButton(
                            selected = selected,
                            onClick = { selected = !selected }
                        )
                    }
                    if (showSwitch) {
                        // Use your CustomSwitchButton composable here
                        CustomSwitchButton(
                            checked = false,
                            onCheckedChange = {}
                        )
                    }
                    if (showArrow) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onArrowClick?.invoke()
                                }
                        )
                    }
                }
            } else {
                // Container 1 UI (like titleTextView, supportingTextView)
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = titleText,
                        fontSize = titleTextFontSize.toSp(),
                        color = titleColor,
                        fontWeight = FontWeight.Medium,
                    )
                    if (supportingText.isNotEmpty()) {
                        Text(
                            text = supportingText,
                            fontSize = supportingTextFontSize.toSp(),
                            color = supportingTextColor,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (showCheckBox) {
                        var checked by remember { mutableStateOf(false) }
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it }
                        )
                    }
                    if (showRadioBtn) {
                        var selected by remember { mutableStateOf(false) }
                        RadioButton(
                            selected = selected,
                            onClick = { selected = !selected }
                        )
                    }
                    if (showSwitch) {
                        CustomSwitchButton(
                            checked = false,
                            onCheckedChange = {}
                        )
                    }
                    if (showArrow) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onArrowClick?.invoke()
                                }
                        )
                    }
                }
            }
        }
    }
}

/** Extension functions to convert between sp and dp **/
fun Dp.toSp(): androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit(this.value, androidx.compose.ui.unit.TextUnitType.Sp)
fun androidx.compose.ui.unit.TextUnit.toDp(): Dp = Dp(this.value)

