package com.muratdayan.epasaj.presentation.compose.onboarding

import android.content.Context
import com.muratdayan.epasaj.presentation.base.SharedPrefManager

// onboarding'in kullanıcıya yalnız 1 defa gösterilmesi işlemi için shared pref kaydedilmesi
class OnBoardingUtils(context: Context){


    val sharedPrefManager = SharedPrefManager(context, "onboardingComplete")

    fun isOnBoardingComplete():Boolean{
        val isOnBoardingComplete = sharedPrefManager.getValue("isOnBoardComplete",false)
        return  isOnBoardingComplete
    }

    fun setOnBoardingComplete(){
        sharedPrefManager.setValue("isOnBoardComplete",true)
    }
}