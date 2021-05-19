package com.geekbrains.weatherwithmvvm.experiments

import android.app.Service
import android.content.Intent
import android.os.IBinder

class UsualService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //... наш код сервиса. Здесь поток UI! Или тот, с которого вы запустили ваш сервис
        Thread {
            //фоновый поток
            stopSelf()
        }.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}