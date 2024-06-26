package com.muratdayan.epasaj.presentation.compose.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.base.components.TextItem
import com.muratdayan.epasaj.presentation.compose.onboarding.OnBoardingModel

@Composable
fun OnBoardingGraphUI(onBoardingModel: OnBoardingModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = onBoardingModel.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_llarge_20), 0.dp),
            alignment = Alignment.Center
        )


        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_xlarge_40)))

        TextItem(
            text = onBoardingModel.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large_16), 0.dp),
            fontSizeRes = R.dimen.text_size_h5_16,
            textAlign = TextAlign.Center,
            textColor = colorResource(id = R.color.md_theme_onBackground)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingGraphUIPreview(){
    OnBoardingGraphUI(onBoardingModel = OnBoardingModel.FirstPages)
}