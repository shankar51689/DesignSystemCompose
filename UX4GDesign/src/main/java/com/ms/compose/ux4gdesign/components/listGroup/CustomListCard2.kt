package com.ms.compose.ux4gdesign.components.listGroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomListCard2Compose(
    modifier: Modifier = Modifier,
    titleText: String = "List Item",
    supportingText: String = "",
    titleTextFontSize: TextUnit = 14.sp,
    supportingTextFontSize: TextUnit = 14.sp,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    supportingTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface,

    // Visibility flags
    showCheckBox: Boolean = false,
    showArrow: Boolean = false,
    showSwitch: Boolean = false,
    showRadioBtn: Boolean = false,

    // Callbacks
    onArrowClick: (() -> Unit)? = null,
    onCheckBoxClick: ((Boolean) -> Unit)? = null,
    onRadioButtonClick: ((Boolean) -> Unit)? = null,
    onSwitchClick: ((Boolean) -> Unit)? = null
) {
    var isChecked by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }
    var isSwitchOn by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth().padding(10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titleText,
                    fontSize = titleTextFontSize,
                    color = titleColor,
                    fontWeight = FontWeight.Medium
                )
                if (supportingText.isNotEmpty()) {
                    Text(
                        text = supportingText,
                        fontSize = supportingTextFontSize,
                        color = supportingTextColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Right-side actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showCheckBox) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                            onCheckBoxClick?.invoke(it)
                        }
                    )
                }

                if (showRadioBtn) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            isSelected = !isSelected
                            onRadioButtonClick?.invoke(isSelected)
                        }
                    )
                }

                if (showSwitch) {
                    Switch(
                        checked = isSwitchOn,
                        onCheckedChange = {
                            isSwitchOn = it
                            onSwitchClick?.invoke(it)
                        }
                    )
                }

                if (showArrow) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onArrowClick?.invoke() }
                    )
                }
            }
        }
    }
}
