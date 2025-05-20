package com.ms.compose.ux4gdesign.components.editTextView

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TextFieldState { DEFAULT, ERROR, WARNING, SUCCESS }

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String = "",
    labelTextSize: TextUnit = 14.sp,
    labelTextColor: Color = Color.Black,
    hintText: String = "",
    hintTextSize: TextUnit = 16.sp,
    hintTextColor: Color = Color.Gray,
    shape: Shape = RoundedCornerShape(10.dp),
    borderWidth: Dp = 1.dp,
    defaultColor: Color = Color.LightGray,
    errorColor: Color = Color.Red,
    warningColor: Color = Color(0xFFFFA500), // orange
    successColor: Color = Color.Green,
    state: TextFieldState = TextFieldState.DEFAULT,
    isPassword: Boolean = false,
    drawableStart: Int? = null,
    drawableEnd: Int? = null,
    messageText: String = ""
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val borderColor = when (state) {
        TextFieldState.DEFAULT -> defaultColor
        TextFieldState.ERROR -> errorColor
        TextFieldState.WARNING -> warningColor
        TextFieldState.SUCCESS -> successColor
    }

    val messageIcon = when (state) {
        TextFieldState.DEFAULT -> null
        TextFieldState.ERROR -> Icons.Default.Clear
        TextFieldState.WARNING -> Icons.Default.Info
        TextFieldState.SUCCESS -> Icons.Default.Info
    }

    Column(modifier = modifier) {
        if (labelText.isNotEmpty()) {
            Text(
                text = labelText,
                fontSize = labelTextSize,
                color = labelTextColor
            )
            Spacer(Modifier.height(4.dp))
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = hintText,
                    fontSize = hintTextSize,
                    color = hintTextColor
                )
            },
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
            ),
            shape = shape,
            singleLine = true,
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isPassword) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                        )
                    } else if (value.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable { onValueChange("") }
                        )
                    }
                    drawableEnd?.let { res ->
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            },
            leadingIcon = drawableStart?.let { res ->
                {
                    Icon(
                        painter = painterResource(id = res),
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .border(borderWidth, borderColor, shape)
        )

        if (state != TextFieldState.DEFAULT && messageText.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                messageIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = borderColor
                    )
                    Spacer(Modifier.width(4.dp))
                }
                Text(
                    text = messageText,
                    color = borderColor,
                    fontSize = hintTextSize
                )
            }
        }
    }
}
