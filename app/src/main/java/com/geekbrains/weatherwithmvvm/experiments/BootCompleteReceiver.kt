package com.geekbrains.weatherwithmvvm.experiments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat

class BootCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Boot receiver", Toast.LENGTH_SHORT).show()
        context?.let {
            ContextCompat.startForegroundService(it, Intent(it, MyForegroundService::class.java))
        }
    }
}