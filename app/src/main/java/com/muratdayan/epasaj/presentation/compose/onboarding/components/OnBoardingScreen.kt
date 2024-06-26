package com.muratdayan.epasaj.presentation.compose.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.base.components.ButtonUI
import com.muratdayan.epasaj.presentation.compose.onboarding.OnBoardingModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onFinished: () -> Unit
) {

    val pages =
        listOf(OnBoardingModel.FirstPages, OnBoardingModel.SecondPages, OnBoardingModel.ThirdPages)

    // yatay kaydırılabilen ekran
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    // butonların state duurumuna göre title değerleri
    val buttonState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Next")
                2 -> listOf("Back", "Start")
                else -> listOf("", "")
            }
        }
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.padding_medium_8),
                        dimensionResource(id = R.dimen.padding_medium_8)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (buttonState.value[0].isNotEmpty()) {
                        ButtonUI(
                            text = buttonState.value[0],
                            backgroundColor = colorResource(id = R.color.md_theme_onPrimary),
                            textColor = colorResource(id = R.color.md_theme_outlineVariant_mediumContrast),
                            onClick = {
                                scope.launch {
                                    // geride sayfa varsa back ile geriye gidilir
                                    if (pagerState.currentPage > 0) {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    IndicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage)

                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    ButtonUI(
                        text = buttonState.value[1],
                        backgroundColor = colorResource(id = R.color.md_theme_primary),
                        textColor = colorResource(id = R.color.md_theme_onPrimary),
                        onClick = {
                            // sayfa varsa next yapılır yoksa onfinished diyerek logine gidilir
                            scope.launch {
                                if (pagerState.currentPage < pages.size -1){
                                    pagerState.animateScrollToPage(pagerState.currentPage +1)
                                }else{
                                    onFinished()
                                }
                            }
                        }
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                HorizontalPager(state = pagerState) { index ->
                    OnBoardingGraphUI(onBoardingModel = pages[index])
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(onFinished = {})
}