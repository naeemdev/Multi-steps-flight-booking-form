package com.naeemdev.multistepsflightbookingform.presentation.component.datepicker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
fun getCalendarAtMidnight(timeMillis: Long? = null, addDays: Int = 0): Calendar {
    val calendar = Calendar.getInstance()
    timeMillis?.let { calendar.timeInMillis = it }
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    if (addDays != 0) {
        calendar.add(Calendar.DAY_OF_YEAR, addDays)
    }
    return calendar
}

@OptIn(ExperimentalMaterial3Api::class)
object SelectableDatesForPastOnly : SelectableDates {
    private val todayAtMidnightMillis: Long by lazy {
        getCalendarAtMidnight().timeInMillis
    }
    private val currentYear: Int by lazy {
        Calendar.getInstance().get(Calendar.YEAR)
    }

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis < todayAtMidnightMillis // Strictly before today
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= currentYear
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object SelectableDatesForCurrentAndFuture : SelectableDates {
    private val todayAtMidnightMillis: Long by lazy {
        getCalendarAtMidnight().timeInMillis
    }
    private val currentYear: Int by lazy {
        Calendar.getInstance().get(Calendar.YEAR)
    }

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= todayAtMidnightMillis // Today or any day after
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= currentYear
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object SelectableDatesForFutureOnlyStrict : SelectableDates {
    private val tomorrowAtMidnightMillis: Long by lazy {
        getCalendarAtMidnight(addDays = 1).timeInMillis
    }
    private val currentYear: Int by lazy {
        Calendar.getInstance().get(Calendar.YEAR)
    }

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= tomorrowAtMidnightMillis // Tomorrow or any day after
    }

    override fun isSelectableYear(year: Int): Boolean {
        // A future date can be in the current year
        return year >= currentYear
    }
}