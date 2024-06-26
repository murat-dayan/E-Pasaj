package com.muratdayan.epasaj.presentation.notification

import javax.inject.Inject
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class NotificationViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String> get() = _notification

    // title ve mesaj değerlerinin broadcast üzerinden alınması
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val title = intent?.getStringExtra("title") ?: "No Title"
            val body = intent?.getStringExtra("body") ?: "No Body"
            _notification.value = "$title: $body"
        }
    }

    // uygulamaya geçiş
    init {
        LocalBroadcastManager.getInstance(application).registerReceiver(
            broadcastReceiver,
            IntentFilter("com.muratdayan.epasaj.NOTIFICATION")
        )
    }

    // broadcast nesnesinin silinişi
    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(getApplication()).unregisterReceiver(broadcastReceiver)
    }
}
