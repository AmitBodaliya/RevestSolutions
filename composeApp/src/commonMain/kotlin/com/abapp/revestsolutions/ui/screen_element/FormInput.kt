package com.abapp.revestsolutions.ui.screen_element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp



@Composable
fun FormInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    title: String = "",
    error: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    startIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onDone: () -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {

        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    focusManager.clearFocus()
                }
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Gray.copy(alpha = 0.1f),
                    RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            decorationBox = { innerTextField ->

                Row(verticalAlignment = Alignment.CenterVertically) {

                    startIcon?.invoke()

                    if (startIcon != null) {
                        Spacer(Modifier.width(8.dp))
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        }

                        innerTextField()
                    }

                    trailingContent?.invoke()
                }
            }
        )

        if (!error.isNullOrEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}