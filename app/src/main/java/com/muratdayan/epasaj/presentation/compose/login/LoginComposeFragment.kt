package com.muratdayan.epasaj.presentation.compose.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.presentation.SharedViewModel
import com.muratdayan.epasaj.presentation.compose.login.components.LoginScreen
import com.muratdayan.epasaj.presentation.compose.onboarding.OnBoardingUtils
import com.muratdayan.epasaj.presentation.compose.onboarding.components.OnBoardingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginComposeFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val loginViewModel: LoginViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val onBoardingUtils by lazy {
        OnBoardingUtils(requireContext())
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also { it ->

            composeView = it
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // kullanıcı remember me seçmişse yapılacaklar
        navController = Navigation.findNavController(view)
        val sharedPrefManagerRememberMe = SharedPrefManager(requireContext(), "remember_me")
        val isRememberMeCheckedValue = sharedPrefManagerRememberMe.getValue("remember_me", false)
        if (isRememberMeCheckedValue) {
            navController.navigate(R.id.navigate_loginFragment_to_productFragment)
        }

        composeView.setContent {
            Surface(
                color = colorResource(id = R.color.md_theme_background)
            ) {
                if (onBoardingUtils.isOnBoardingComplete()) {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        context = requireContext()
                    )
                } else {
                    ShowOnBoardingScreen()
                }
            }
        }
    }


    @Composable
    private fun ShowOnBoardingScreen() {
        val scope = rememberCoroutineScope()

        OnBoardingScreen {
            onBoardingUtils.setOnBoardingComplete()
            scope.launch {
                composeView.setContent {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        context = requireContext()
                    )
                }
            }
        }
    }

}

