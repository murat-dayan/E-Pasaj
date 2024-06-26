package com.muratdayan.epasaj.presentation.compose.login.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.SharedViewModel
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.presentation.base.components.ButtonUI
import com.muratdayan.epasaj.presentation.base.components.TextItem
import com.muratdayan.epasaj.presentation.compose.login.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    context: Context,
    loginViewModel: LoginViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    var loginAttempted by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("oliviaw") }
    var password by remember { mutableStateOf("oliviawpass") }
    var isRememberMeChecked by remember { mutableStateOf(false) }

    // state listened
    val userState = loginViewModel.userState.collectAsState()

    val scope = rememberCoroutineScope()


    //shared Preff
    val sharedPrefManagerUserData = SharedPrefManager(context, "user_data")
    val sharedPrefManagerRememberMe = SharedPrefManager(context, "remember_me")
    sharedPrefManagerRememberMe.setValue("remember_me", isRememberMeChecked)

    // if login success
    if (loginAttempted && userState.value.userModel != null) {
        sharedPrefManagerUserData.setValue("user_id", userState.value.userModel!!.id)
        sharedPrefManagerUserData.setValue("user_name", userState.value.userModel!!.username)
        sharedPrefManagerUserData.setValue("user_photo_url", userState.value.userModel!!.image)
        sharedPrefManagerUserData.setValue("user_token", userState.value.userModel!!.token)
        sharedPrefManagerUserData.setValue("user_refresh_token", userState.value.userModel!!.refreshToken)
        navController.navigate(R.id.navigate_loginFragment_to_productFragment)
        loginAttempted = false // Reset the flag
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.md_theme_primary))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xlarge_32)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.md_theme_primary)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                TextItem(
                    text = "Welcome to E-Pasaj",
                    modifier = Modifier.fillMaxWidth(),
                    fontSizeRes = R.dimen.text_size_h1_24
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_xlarge_32)))
                TextItem(
                    text = "Login to your account",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_xxlarge_64)))
            Column {
                OutlinedTextFieldComp(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = "Username")

                OutlinedTextFieldComp(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = "Password"
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small_4)))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextItem(text = "Remember Me")
                    Checkbox(
                        checked = isRememberMeChecked,
                        onCheckedChange = {
                            isRememberMeChecked = !isRememberMeChecked
                        },
                        modifier = Modifier
                            .padding(end = dimensionResource(id = R.dimen.padding_small_4)),
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = colorResource(id = R.color.md_theme_primary), // İşaret rengini beyaz yapar
                            checkedColor = colorResource(id = R.color.md_theme_background), // Seçiliyken arkaplan rengi
                            uncheckedColor = colorResource(id = R.color.md_theme_background), // Seçili olmayan arkaplan rengi
                        )
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large_16)))
                ButtonUI(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = colorResource(id = R.color.md_theme_onPrimary),
                    textColor = colorResource(id = R.color.md_theme_primary),
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()){
                            scope.launch {
                                sharedPrefManagerRememberMe.setValue("remember_me", isRememberMeChecked)
                                loginViewModel.login(username,password)
                                loginAttempted = true
                            }
                        }else{
                            Toast.makeText(context,"Please enter username and password",Toast.LENGTH_SHORT).show()
                        }
                    },
                    text = "Login"
                )
            }
        }
    }
}



/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //LoginScreen()
}*/
