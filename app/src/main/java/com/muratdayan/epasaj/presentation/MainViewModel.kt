package com.muratdayan.epasaj.presentation

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
): ViewModel(){

    private val _appColor = MutableLiveData<Int>()
    val appColor: LiveData<Int> get() = _appColor

    private val _appTitle = MutableLiveData<String>()
    val appTitle: LiveData<String> get() = _appTitle

    init {
        fetchAndActivateConfig()
        setupRemoteConfigListener()
    }

    // remote config activate
    private fun fetchAndActivateConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("MainViewModel", "Fetch and activate succeeded")
                    updateAppColor()
                } else {
                    Log.e("MainViewModel", "Fetch failed")
                }
            }
    }

    // remote config değerlerini dinler
    private fun setupRemoteConfigListener() {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.e("MainViewModel", "Updated keys: ${configUpdate.updatedKeys}")

                if (configUpdate.updatedKeys.contains("toolbar_color")) {
                    remoteConfig.activate().addOnCompleteListener {
                        if (it.isSuccessful) {
                            updateAppColor()
                        }
                    }
                }
                if (configUpdate.updatedKeys.contains("app_title")) {
                    remoteConfig.activate().addOnCompleteListener {
                        if (it.isSuccessful) {
                            val title = remoteConfig.getString("app_title")
                            _appTitle.value = title
                            Log.e("MainViewModel", "Updated app title: $title")
                        }
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("MainViewModel", "Config update error with code: ${error.code}", error)
            }
        })
    }

    // remote configdeki değer değişti ise coloru değiştirir
    private fun updateAppColor() {
        val colorString = remoteConfig.getString("toolbar_color")
        if (colorString.isNotEmpty()) {
            try {
                val color = Color.parseColor(colorString)
                _appColor.value = color
                Log.e("MainViewModel", "Updated app color: $colorString")
            } catch (e: IllegalArgumentException) {
                Log.e("MainViewModel", "Invalid color format in Remote Config: $colorString", e)
            }
        } else {
            Log.e("MainViewModel", "Empty or invalid color string received from Remote Config")
        }
    }

}