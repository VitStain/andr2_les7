package com.geekbrains.weatherwithmvvm.interactors.strings_interactor

import android.content.Context
import com.geekbrains.weatherwithmvvm.R

class StringsInteractorImpl(private val context: Context) : StringsInteractor {
    override val errorStr = context.getString(R.string.error)
    override val startStr = context.getString(R.string.start)
}