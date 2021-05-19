package com.geekbrains.weatherwithmvvm.experiments

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class ServiceWithThread() : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        println("JOB SERVICE WORK IN THREAD")
        //...делаем какую-то работу и отправляем результат
        sendMyBroadcast()
    }

    private fun sendMyBroadcast() {
        val broadcastIntent = Intent()
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, true)
        broadcastIntent.action = INTENT_ACTION_KEY
        sendBroadcast(broadcastIntent)
    }

    companion object {
        fun start(context: Context, intent: Intent) {
            enqueueWork(context, ServiceWithThread::class.java, 3322, intent)
        }
    }
}

const val INTENT_ACTION_KEY = "com.geekbrains.weatherwithmvvm.SERVICE_FINISHED_EVENT"
const val INTENT_SERVICE_DATA = "INTENT_SERVICE_DATA"