package com.geekbrains.weatherwithmvvm.model

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd.MMM.yy HH:mm"

fun Date.format(): String {
    return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(this)
}
fun View.showSnackBar(
        text: String,
        actionText: String,
        action: ((View) -> Unit)? = null,
        length: Int = Snackbar.LENGTH_INDEFINITE
) {
    val ourSnackBar = Snackbar.make(this, text, length)
    action?.let {
        ourSnackBar.setAction(actionText, it)
    }
    ourSnackBar.show()
}
