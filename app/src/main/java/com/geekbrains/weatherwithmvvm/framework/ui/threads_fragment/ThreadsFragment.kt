package com.geekbrains.weatherwithmvvm.framework.ui.threads_fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.geekbrains.weatherwithmvvm.databinding.FragmentThreadsBinding
import com.geekbrains.weatherwithmvvm.experiments.*
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.*
import java.util.concurrent.TimeUnit

class ThreadsFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var binding: FragmentThreadsBinding

    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("FROM SERVICE: ${intent?.getBooleanExtra(INTENT_SERVICE_DATA, false)}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { onStartClick() }

        val usualServiceIntent = Intent(requireContext(), UsualService::class.java)
        activity?.startService(usualServiceIntent)
        activity?.stopService(usualServiceIntent)

        ServiceWithThread.start(requireContext(), Intent(requireContext(), ServiceWithThread::class.java))
        ContextCompat.startForegroundService(requireContext(), Intent(requireContext(), MyForegroundService::class.java))
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(testReceiver, IntentFilter(INTENT_ACTION_KEY))
    }

    override fun onStop() {
        activity?.unregisterReceiver(testReceiver)
        super.onStop()
    }

    private fun onStartClick() = with(binding) {
        val handler = Handler(Looper.getMainLooper())
        var runnable: Runnable? = null
        runnable  = Runnable {
            //...
            handler.postDelayed(runnable!!, 1000)
        }
        handler.postDelayed(runnable, 1000)
        handler.removeCallbacks(runnable)

        launch {
            val task = async(Dispatchers.Default) {
                val value = try { editText.text.toString().toInt() } catch (exc: Exception) { 1 }
                startCalculations(value)
            }
            textView.text = task.await()
        }
    }

    private fun startCalculations(seconds: Int): String {
        val date = Date()
        var diffInSec: Long
        do {
            val currentDate = Date()
            val diffInMs: Long = currentDate.time - date.time
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        } while (diffInSec < seconds)
        return diffInSec.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThreadsFragment()
    }
}