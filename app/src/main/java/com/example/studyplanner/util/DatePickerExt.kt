package com.example.studyplanner.util

import android.widget.DatePicker
import java.util.*

fun DatePicker.getDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)
    return calendar.time
}