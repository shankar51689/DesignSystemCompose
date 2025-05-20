package com.ms.compose.ux4gdesign.components.editTextView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomMultiLineEditText(
    modifier: Modifier = Modifier,
    labelText: String = "Label",
    hintText: String = "Placeholder",
    text: String,
    onTextChange: (String) -> Unit,
    state: EditTextState = EditTextState.DEFAULT,
    maxLength: Int = 200,
    textSize: TextUnit = 14.sp,
    labelSize: TextUnit = 16.sp,
    cornerRadius: Dp = 12.dp,
    strokeWidth: Dp = 2.dp
) {
    val borderColor = when (state) {
        EditTextState.DEFAULT -> Color(0xFFBDBDBD) // UX4G_neutral_300
        EditTextState.ERROR -> Color(0xFFD32F2F)   // UX4G_danger
        EditTextState.WARNING -> Color(0xFFFFA000) // UX4G_warning
        EditTextState.SUCCESS -> Color(0xFF388E3C) // UX4G_success
    }

    Column(modifier = modifier) {
        Text(
            text = labelText,
            fontSize = labelSize,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent, RoundedCornerShape(cornerRadius))
                .border(strokeWidth, borderColor, RoundedCornerShape(cornerRadius))
                .padding(8.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    if (it.length <= maxLength) onTextChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = textSize,
                    color = Color.Black
                ),
                maxLines = Int.MAX_VALUE,
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = hintText,
                            fontSize = textSize,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )
        }

        Text(
            text = "${text.length}/$maxLength",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
        )
    }
}

enum class EditTextState { DEFAULT, ERROR, WARNING, SUCCESS }
