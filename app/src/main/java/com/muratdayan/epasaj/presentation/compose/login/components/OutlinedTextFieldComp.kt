package com.muratdayan.epasaj.presentation.compose.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.base.components.TextItem

@Composable
fun OutlinedTextFieldComp(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { TextItem(text = label, fontSizeRes = R.dimen.text_size_h5_16) },
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_large_24)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            focusedBorderColor = colorResource(id = R.color.md_theme_background),
            unfocusedBorderColor = colorResource(id = R.color.md_theme_background),
            textColor = colorResource(id = R.color.md_theme_background),
            cursorColor = colorResource(id = R.color.md_theme_background),
            placeholderColor = colorResource(id = R.color.md_theme_background),
        )
    )
}