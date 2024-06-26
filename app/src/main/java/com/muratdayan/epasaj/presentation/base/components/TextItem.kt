package com.muratdayan.epasaj.presentation.base.components

import androidx.annotation.DimenRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.muratdayan.epasaj.R

@Composable
fun TextItem(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = colorResource(id = R.color.md_theme_background),
    @DimenRes fontSizeRes: Int = R.dimen.text_size_h3_20,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center
) {
    val context = LocalContext.current
    val fontSize = with(LocalDensity.current) {
        context.resources.getDimension(fontSizeRes).toSp()
    }

    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign
        )
}

@Preview(showBackground = true)
@Composable
fun TextItemPreview() {
    TextItem(text = "Hello")
}