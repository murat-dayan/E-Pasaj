package com.muratdayan.epasaj.presentation.base.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.muratdayan.epasaj.R


@Composable
fun ButtonUI(
    modifier: Modifier = Modifier,
    text: String = "Next",
    backgroundColor: Color = colorResource(id = R.color.md_theme_primary),
    textColor: Color = colorResource(id = R.color.md_theme_onPrimary),
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_large_24)),
    ) {

        TextItem(text = text, textColor = textColor)
    }
}

