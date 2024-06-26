package com.muratdayan.epasaj.presentation.compose.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.stringResource
import com.muratdayan.epasaj.R

// her bir onboarding sayfasının model olarak temsili
sealed class OnBoardingModel(
    @DrawableRes val image:Int,
    val description:String
){
    data object FirstPages : OnBoardingModel(
        image = R.drawable.onb_image1,
        description = "Explore trending styles and discover new favorites."
    )

    data object SecondPages : OnBoardingModel(
        image = R.drawable.onb_image2,
        description = "Discover unique styles that fit your personality perfectly."
    )

    data object ThirdPages : OnBoardingModel(
        image = R.drawable.onb_image3,
        description = "Find fashion inspiration from a curated selection of top brands."
    )
}