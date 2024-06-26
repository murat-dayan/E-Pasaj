package com.muratdayan.epasaj.presentation.compose.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muratdayan.epasaj.R

@Composable
fun IndicatorUI(
    pageSize:Int,
    currentPage:Int,
    selectedColor: Color = colorResource(id = R.color.md_theme_primary),
    unSelectedColor: Color = colorResource(id = R.color.md_theme_outlineVariant)
){

    Row (
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        repeat(pageSize){
            Spacer(modifier = Modifier.size(2.5.dp))
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.padding_large_16))
                    .width(dimensionResource(id = R.dimen.padding_large_16))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_medium_8)))
                    .background(color = if (it == currentPage) selectedColor else unSelectedColor)
            ){

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorPreview(){
    IndicatorUI(pageSize = 3, currentPage = 1)
}