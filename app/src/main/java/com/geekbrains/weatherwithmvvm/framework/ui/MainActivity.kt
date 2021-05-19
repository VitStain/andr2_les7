package com.geekbrains.weatherwithmvvm.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.geekbrains.weatherwithmvvm.R
import com.geekbrains.weatherwithmvvm.databinding.MainActivityBinding
import com.geekbrains.weatherwithmvvm.framework.ui.main.MainFragment
import com.geekbrains.weatherwithmvvm.framework.ui.threads_fragment.ThreadsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dialog = AlertDialog.Builder(baseContext)
        dialog.setPositiveButton("Yes") {
            dialog, which -> //TODO("Not yet implemented")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ThreadsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}